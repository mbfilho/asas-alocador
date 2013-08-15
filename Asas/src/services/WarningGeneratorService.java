package services;

import java.util.HashSet;
import java.util.Vector;


import basic.Classroom;
import basic.Professor;
import basic.SlotRange;
import basic.Class;

import statePersistence.StateService;
import utilities.CollectionUtil;
import warnings.WarningReport;
import warnings.WarningReportList;
import warnings.types.ClassWithoutProfessorWarning;
import warnings.types.ClassWithoutRoomWarning;
import warnings.types.ClassWithoutSlotWarning;
import warnings.types.DeallocatedProfessorWarning;
import warnings.types.SameProfessorsWarning;
import warnings.types.SameRoomWarning;
import warnings.types.Warning;

public class WarningGeneratorService {

	private StateService stateService = StateService.getInstance();
	private ClassService classService;
	private ProfessorService professorService;
	protected AllowedWarningsService allowedWarningService;
	
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
	
	public Vector<Warning> getSameRoomConflicts(){
		return getSameRoomConflicts(new Vector<Class>(classService.all()));
	}
	
	protected Vector<Warning> getSameRoomConflicts(Vector<Class> allClasses){
		Vector<Warning> warnings = new Vector<Warning>();
		
		for(int i = 0; i < allClasses.size(); ++i){
			for(int j = i + 1; j < allClasses.size(); ++j){
				Class c1 = allClasses.get(i), c2 = allClasses.get(j);
				
				for(SlotRange r1 : c1.getSlots()){
					if(r1.getClassroom() == null || ignoreRoom(r1.getClassroom())) continue;
					for(SlotRange r2 : c2.getSlots()){
						if(r2.getClassroom() != r1.getClassroom()) continue;
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
	
	
	public Vector<Warning> getSameProfConflicts(){
		return getSameProfConflicts(new Vector<Class>(classService.all()));
	}
	
	protected Vector<Warning> getSameProfConflicts(Vector<Class> allClasses){
		Vector<Warning> warnings = new Vector<Warning>();
		
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
				warnings.add(new ClassWithoutRoomWarning(c, slotsWithoutRoom));
		}
		
		return warnings;
	}
	
	public int notAllowedWarningsCount(){
		int cont = 0;
		for(WarningReport report : getWarningReportList()){
			for(Warning w : report.getAllWarnings()){
				if(!allowedWarningService.isAllowed(w)) ++cont;
			}
		}
		
		return cont;
	}
	
	public WarningReportList getWarningReportList(){
		WarningReportList report = new WarningReportList();
		
		report.addReport(new WarningReport("Mesma sala e horário", getSameRoomConflicts()));
		report.addReport(new WarningReport("Professores desalocados", checkProfessorsWithoutClasses()));
		report.addReport(new WarningReport("Mesmos professores e horários", getSameProfConflicts()));
		report.addReport(new WarningReport("Turmas sem professor", checkClassHasProfs()));
		report.addReport(new WarningReport("Turmas sem horário", checkClassHasSlot()));
		report.addReport(new WarningReport("Turmas sem sala", checkClassHasRoom()));
		return report;
	}
	
}

