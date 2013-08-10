package services;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import classEditor.NamedPair;

import basic.Classroom;
import basic.Professor;
import basic.SlotRange;
import basic.Class;

import statePersistence.StateService;
import utilities.CollectionUtil;
import utilities.StringUtil;
import warnings.AllowedWarningsService;
import warnings.ClassWithoutProfessorWarning;
import warnings.ClassWithoutRoom;
import warnings.ClassWithoutSlotWarning;
import warnings.DeallocatedProfessorWarning;
import warnings.SameProfessorsWarning;
import warnings.SameRoomWarning;
import warnings.Warning;
import warnings.WarningReport;

public class WarningGeneratorService {

	private StateService stateService = StateService.getInstance();
	private ClassService classService;
	private ProfessorService professorService;
	private AllowedWarningsService allowedWarningService;
	
	public WarningGeneratorService(){
		classService = new ClassService();
		professorService = new ProfessorService();
		allowedWarningService = new AllowedWarningsService();
	}
	
	//@TODO tratar isso direito
	private boolean ignoreRoom(Classroom room){
		return room.getName().compareToIgnoreCase("Area ii") == 0
				|| room.getName().compareToIgnoreCase("ctg") == 0;
	}
	
	public Vector<Warning> checkSameRoomConflicts(){
		Vector<Warning> warnings = new Vector<Warning>();
		Vector<Class> allClasses = stateService.getCurrentState().classes.all();

		for(int i = 0; i < allClasses.size(); ++i){
			for(int j = i + 1; j < allClasses.size(); ++j){
				Class c1 = allClasses.get(i), c2 = allClasses.get(j);
				
				for(SlotRange r1 : c1.getSlots()){
					if(r1.getClassroom() == null || ignoreRoom(r1.getClassroom())) continue;
					for(SlotRange r2 : c2.getSlots()){
						if(r2.getClassroom() != r1.getClassroom() || ignoreRoom(r1.getClassroom())) continue;
						SlotRange interseption = r1.intersection(r2);
						if(!interseption.isValid()) continue;
						
						Warning w = new SameRoomWarning(r1.getClassroom(), c1, c2, interseption);
						warnings.add(w);
					}
				}
				
			}
		}
		return warnings;
	}
	
	public Vector<Warning> checkProfessorsWithoutClasses(){
		HashSet<Professor> allocatedProfessors = new HashSet<Professor>();
		Vector<Warning> warnings = new Vector<Warning>();
		for(Class c : classService.all()) allocatedProfessors.addAll(c.getProfessors());
		
		for(Professor p : professorService.all()){
			if(!allocatedProfessors.contains(p) && !p.isAway()) 
				warnings.add(new DeallocatedProfessorWarning(p));
		}
		return warnings;
	}
	
	public Vector<Warning> checkSameProfConflicts(){
		Vector<Warning> warnings = new Vector<Warning>();
		Vector<Class> allClasses = new Vector<Class>(classService.all());
		
		for(int i = 0; i < allClasses.size(); ++i){
			for(int j = i + 1; j < allClasses.size(); ++j){
				Class c1 = allClasses.get(i), c2 = allClasses.get(j);
				
				Vector<SlotRange> slotIntersection = new Vector<SlotRange>();
				for(SlotRange r1 : c1.getSlots()) for(SlotRange r2 : c2.getSlots()){
					if(r1.intersects(r2)) slotIntersection.add(r1.intersection(r2));
				}
				if(slotIntersection.isEmpty()) continue;
				
				Vector<Professor> profIntersection = CollectionUtil.intersectLists(c1.getProfessors(), c2.getProfessors());
				if(profIntersection.isEmpty()) continue;
				
				Warning w = new SameProfessorsWarning(c1, c2, profIntersection, slotIntersection);
				warnings.add(w);
			}
		}
		return warnings;
	}
	
	public Vector<Warning> checkClassHasProfs(){
		Vector<Warning> warnings = new Vector<Warning>();
		
		for(Class c : stateService.getCurrentState().classes.all()){
			if(c.getProfessors().isEmpty())
				warnings.add(new ClassWithoutProfessorWarning(c));
		}
		
		return warnings;
	}
	
	public Vector<Warning> checkClassHasSlot(){
		Vector<Warning> warnings = new Vector<Warning>();
		
		for(Class c : stateService.getCurrentState().classes.all()){
			if(c.getSlots().isEmpty())
				warnings.add(new ClassWithoutSlotWarning(c));
		}
		return warnings;
	}

	public Vector<Warning> checkClassHasRoom(){
		Vector<Warning> warnings = new Vector<Warning>();
		for(Class c : stateService.getCurrentState().classes.all()){
			Vector<SlotRange> slotsWithoutRoom = new Vector<SlotRange>();
			for(SlotRange range : c.getSlots()){
				if(range.getClassroom() == null) slotsWithoutRoom.add(range);
			}
			if(!slotsWithoutRoom.isEmpty())
				warnings.add(new ClassWithoutRoom(c, slotsWithoutRoom));
		}
		
		return warnings;
	}
	
	public int notAllowedWarningsCount(){
		int cont = 0;
		for(NamedPair<Vector<Warning>> warningList : getAllWarnings().getAllReports()){
			for(Warning w : warningList.data){
				if(!allowedWarningService.isAllowed(w)) ++cont;
			}
		}
		
		return cont;
	}
	
	public WarningReport getAllWarnings(){
		WarningReport report = new WarningReport();
		
		report.addReport("Mesma sala e horário", checkSameRoomConflicts());
		report.addReport("Professores desalocados", checkProfessorsWithoutClasses());
		report.addReport("Mesmos professores e horários", checkSameProfConflicts());
		report.addReport("Turmas sem professor", checkClassHasProfs());
		report.addReport("Turmas sem horário", checkClassHasSlot());
		report.addReport("Turmas sem sala", checkClassHasRoom());
		return report;
	}
	
}

