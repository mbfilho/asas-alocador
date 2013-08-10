package warnings;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import classEditor.NamedPair;

import basic.Classroom;
import basic.Professor;
import basic.SlotRange;
import basic.Class;

import services.ClassService;
import services.ProfessorService;
import statePersistence.StateService;
import utilities.CollectionUtil;
import utilities.StringUtil;
import validation.Warning;

public class TheService {

	private StateService stateService = StateService.getInstance();
	private ClassService classService;
	private ProfessorService professorService;
	
	public TheService(){
		classService = new ClassService();
		professorService = new ProfessorService();
	}
	
	//@TODO tratar isso direito
	private boolean ignoreRoom(Classroom room){
		return room.getName().compareToIgnoreCase("Area ii") == 0
				|| room.getName().compareToIgnoreCase("ctg") == 0;
	}
	
	public Vector<_Warning> checkSameRoomConflicts(){
		Vector<_Warning> warnings = new Vector<_Warning>();
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
						
						_Warning w = new SameRoomWarning(r1.getClassroom(), c1, c2, interseption);
						warnings.add(w);
					}
				}
				
			}
		}
		return warnings;
	}
	
	public Vector<_Warning> checkProfessorsWithoutClasses(){
		HashSet<Professor> allocatedProfessors = new HashSet<Professor>();
		Vector<_Warning> warnings = new Vector<_Warning>();
		for(Class c : classService.all()) allocatedProfessors.addAll(c.getProfessors());
		
		for(Professor p : professorService.all()){
			if(!allocatedProfessors.contains(p) && !p.isAway()) 
				warnings.add(new DeallocatedProfessorWarning(p));
		}
		return warnings;
	}
	
	public Vector<_Warning> checkSameProfConflicts(){
		Vector<_Warning> warnings = new Vector<_Warning>();
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
				
				_Warning w = new SameProfessorsWarning(c1, c2, profIntersection, slotIntersection);
				warnings.add(w);
			}
		}
		return warnings;
	}
	
	public Vector<_Warning> checkClassHasProfs(){
		Vector<_Warning> warnings = new Vector<_Warning>();
		
		for(Class c : stateService.getCurrentState().classes.all()){
			if(c.getProfessors().isEmpty())
				warnings.add(new ClassWithoutProfessorWarning(c));
		}
		
		return warnings;
	}
	
	public Vector<_Warning> checkClassHasSlot(){
		Vector<_Warning> warnings = new Vector<_Warning>();
		
		for(Class c : stateService.getCurrentState().classes.all()){
			if(c.getSlots().isEmpty())
				warnings.add(new ClassWithoutSlotWarning(c));
		}
		return warnings;
	}

	public Vector<_Warning> checkClassHasRoom(){
		Vector<_Warning> warnings = new Vector<_Warning>();
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
	
	public _WarningReport getAllWarnings(){
		_WarningReport report = new _WarningReport();
		
		report.addReport("Mesma sala e horário", checkSameRoomConflicts());
		report.addReport("Professores desalocados", checkProfessorsWithoutClasses());
		report.addReport("Mesmos professores e horários", checkSameProfConflicts());
		report.addReport("Turmas sem professor", checkClassHasProfs());
		report.addReport("Turmas sem horário", checkClassHasSlot());
		report.addReport("Turmas sem sala", checkClassHasRoom());
		return report;
	}
	
}

