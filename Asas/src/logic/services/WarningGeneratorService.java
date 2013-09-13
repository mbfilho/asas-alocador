package logic.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import logic.dto.WarningReport;
import logic.dto.WarningReportList;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;
import data.persistentEntities.Warning;
import data.persistentEntities.warningsTypes.ClassWithoutProfessorWarning;
import data.persistentEntities.warningsTypes.ClassWithoutRoomWarning;
import data.persistentEntities.warningsTypes.ClassWithoutSlotWarning;
import data.persistentEntities.warningsTypes.DeallocatedProfessorWarning;
import data.persistentEntities.warningsTypes.SameProfessorsWarning;
import data.persistentEntities.warningsTypes.SameRoomWarning;



import utilities.CollectionUtil;

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
	
	public List<Warning> getSameRoomConflicts(){
		return getSameRoomConflicts(new ArrayList<Class>(classService.all()));
	}
	
	protected List<Warning> getSameRoomConflicts(List<Class> allClasses){
		List<Warning> warnings = new LinkedList<Warning>();
		
		for(int i = 0; i < allClasses.size(); ++i){
			for(int j = i + 1; j < allClasses.size(); ++j){
				Class c1 = allClasses.get(i), c2 = allClasses.get(j);
				
				for(SlotRange r1 : c1.getSlots()){
					if(r1.getClassroom() == null || r1.getClassroom().isExternal()) continue;
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
	
	public List<Warning> checkProfessorsWithoutClasses(){
		HashSet<Professor> allocatedProfessors = new HashSet<Professor>();
		List<Warning> warnings = new LinkedList<Warning>();
		for(Class c : classService.all()) allocatedProfessors.addAll(c.getProfessors());
		
		for(Professor p : professorService.all()){
			if(!allocatedProfessors.contains(p) && !p.isAway()) 
				warnings.add(new DeallocatedProfessorWarning(p));
		}
		return warnings;
	}
	
	
	public List<Warning> getSameProfConflicts(){
		return getSameProfConflicts(new ArrayList<Class>(classService.all()));
	}
	
	protected List<Warning> getSameProfConflicts(List<Class> allClasses){
		List<Warning> warnings = new LinkedList<Warning>();
		
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
	
	public List<Warning> checkClassHasProfs(){
		List<Warning> warnings = new LinkedList<Warning>();
		
		for(Class c : stateService.getCurrentState().classes.all()){
			if(c.getProfessors().isEmpty())
				warnings.add(new ClassWithoutProfessorWarning(c));
		}
		
		return warnings;
	}
	
	public List<Warning> checkClassHasSlot(){
		List<Warning> warnings = new LinkedList<Warning>();
		
		for(Class c : stateService.getCurrentState().classes.all()){
			if(c.getSlots().isEmpty())
				warnings.add(new ClassWithoutSlotWarning(c));
		}
		return warnings;
	}

	public List<Warning> checkClassHasRoom(){
		List<Warning> warnings = new LinkedList<Warning>();
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

