package validation;

import java.util.Collection;
import java.util.Vector;

import statePersistence.StateService;

import basic.Classroom;
import basic.NamedEntity;
import basic.Professor;
import basic.Class;
import basic.SlotRange;

public class WarningService {
	
	public static final int SAME_ROOM = 0;
	public static final int SAME_PROFESSOR = 1;
	public static final int NO_CONFLICT = 2;

	private StateService stateService = StateService.getInstance();
	
	private <T extends NamedEntity> String joinListWithSeparator(Iterable<T> list, String separator){
		String joined = "", prefix = "";
		
		for(NamedEntity item : list){
			joined += prefix + item.getName();
			prefix = separator;
		}
		return joined;
	}
	
	private <T> Vector<T> intersectLists(Collection<T> listA, Collection<T> listB){
		Vector<T> resp = new Vector<T>();
		for(T a : listA) if(listB.contains(a)) resp.add(a);
		return resp;
	}
	
	public Vector<Warning> checkClassHasProfs(){
		
		Vector<Warning> warnings = new Vector<Warning>();
		
		for(Class c : stateService.getCurrentState().classes.all()){
			if(c.getProfessors().size() == 0)
				warnings.add(new Warning().addMessage(c.getName()));
		}
		return warnings;
	}
	
	public Vector<Warning> checkClassHasSlot(){
		Vector<Warning> warnings = new Vector<Warning>();
		
		for(Class c : stateService.getCurrentState().classes.all()){
			if(c.getSlots().size() == 0)
				warnings.add(new Warning().addMessage(c.getName()));
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
				warnings.add(new Warning().addMessage(c.getName()).addMessage(joinListWithSeparator(slotsWithoutRoom, "/")));
		}
		
		return warnings;
	}
	
	public Vector<Warning> checkSameProfConflicts(){
		Vector<Warning> warnings = new Vector<Warning>();
		Vector<Class> allClasses = stateService.getCurrentState().classes.all();
		
		for(int i = 0; i < allClasses.size(); ++i){
			for(int j = i + 1; j < allClasses.size(); ++j){
				Class c1 = allClasses.get(i), c2 = allClasses.get(j);
				
				Vector<SlotRange> slotIntersection = new Vector<SlotRange>();
				for(SlotRange r1 : c1.getSlots()) for(SlotRange r2 : c2.getSlots()){
					if(r1.intersects(r2)) slotIntersection.add(r1.intersection(r2));
				}
				if(slotIntersection.isEmpty()) continue;
				
				Vector<Professor> profIntersection = intersectLists(c1.getProfessors(), c2.getProfessors());
				if(profIntersection.isEmpty()) continue;
				
				Warning w = new Warning().addMessage(c1.getName()).addMessage(c2.getName())
						.addMessage(joinListWithSeparator(profIntersection, "/")).addMessage(joinListWithSeparator(slotIntersection, "/"));
				warnings.add(w);
			}
		}
		return warnings;
	}
	
	public Vector<Warning> checkSameRoomConflicts(){
		Vector<Warning> warnings = new Vector<Warning>();
		Vector<Class> allClasses = stateService.getCurrentState().classes.all();

		for(int i = 0; i < allClasses.size(); ++i){
			for(int j = i + 1; j < allClasses.size(); ++j){
				Class c1 = allClasses.get(i), c2 = allClasses.get(j);
				
				for(SlotRange r1 : c1.getSlots()){
					if(r1.getClassroom() == null) continue;
					for(SlotRange r2 : c2.getSlots()){
						if(r2.getClassroom() != r1.getClassroom()) continue;
						SlotRange interseption = r1.intersection(r2);
						if(!interseption.isValid()) continue;
						
						Warning w = new Warning().addMessage(c1.getName()).addMessage(c2.getName())
								.addMessage(r1.getClassroom().getName()).addMessage(interseption.getName());
						warnings.add(w);
					}
				}
				
			}
		}
		return warnings;
	}
	
	public WarningReport getAllWarnings(){
		if(!stateService.hasValidState()) return new WarningReport();

		WarningReport report = new WarningReport();
		report.classesWithoutProf.addAll(checkClassHasProfs());
		report.classesWithoutRoom.addAll(checkClassHasRoom());
		report.classesWithoutSlots.addAll(checkClassHasSlot());
		report.classesWithSameProf.addAll(checkSameProfConflicts());
		report.classesWithSameRoom.addAll(checkSameRoomConflicts());
		return report;
	}
	
	public int hasConflit(SlotRange query, Class current) {
		if(query.getClassroom() == null) throw new RuntimeException();
		
		for(Class other : stateService.getCurrentState().classes.all()){
			if(other.equals(current)) continue;
			for(Professor p : current.getProfessors()){
				if(other.getProfessors().contains(p) && other.getSlots().contains(query)) return SAME_PROFESSOR;
			}
			
			for(SlotRange range : other.getSlots()){
				if(range.intersects(query) && range.getClassroom() == query.getClassroom()) return SAME_ROOM;
			}
		}
		return NO_CONFLICT;
	}
	
}
