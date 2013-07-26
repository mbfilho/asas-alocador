package validation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.plaf.SliderUI;

import statePersistence.StateService;

import basic.Classroom;
import basic.Professor;
import basic.Class;
import basic.SlotRange;
import data.Repository;

public class WarningService {
	
	public static final int SAME_ROOM = 0;
	public static final int SAME_PROFESSOR = 1;
	public static final int NO_CONFLICT = 2;

	private StateService stateService = StateService.getInstance();
	
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
			if(c.getClassroom() == null)
				warnings.add(new Warning().addMessage(c.getName()));
		}
		return warnings;
	}
	
	public Vector<Warning> checkSameProfConflicts(){
		Vector<Warning> warnings = new Vector<Warning>();
		
		for(Class c1 : stateService.getCurrentState().classes.all()){
			for(Class c2 : stateService.getCurrentState().classes.all()){
				if(c1 == c2) continue;
				boolean slotsIntersects = false;
				System.out.println(c1.getId() + " x " + c2.getId());
				if(c1.getId() == 33 && c2.getId() == 11) System.out.println("AKE! " + slotsIntersects);
				SlotRange slotIntersection = null;
				for(SlotRange r1 : c1.getSlots()){
					for(SlotRange r2 : c2.getSlots()){
						if(r1.equals(r2)){
							slotsIntersects = true;
							slotIntersection = r1;
							break;
						}
					}
				}
				
				if(!slotsIntersects) continue;
				boolean profsIntersects = false;
				Professor profIntersection = null;
				for(Professor p1 : c1.getProfessors()){
					if(c2.getProfessors().contains(p1)){
						profsIntersects = true;
						profIntersection = p1;
						break;
					}
				}
				
				if(profsIntersects){
					Warning w = new Warning().addMessage(c1.getName()).addMessage(c2.getName())
							.addMessage(profIntersection.getName()).addMessage(slotIntersection.getName());
					warnings.add(w);
				}
			}
		}
		return warnings;
	}
	
	public Vector<Warning> checkSameRoomConflicts(){
		Vector<Warning> warnings = new Vector<Warning>();
		
		for(Class c1 : stateService.getCurrentState().classes.all()){
			for(Class c2 : stateService.getCurrentState().classes.all()){
				if(c2 == c1) continue;
				if(c1.getClassroom() == null || c2.getClassroom() == null) continue;
				if(c1.getClassroom().equals(c2.getClassroom())){
					boolean slotsConflicts = false;
					SlotRange intersection = null;
					for(SlotRange r1 : c1.getSlots()){
						for(SlotRange r2 : c2.getSlots()){
							if(r2.equals(r1)){
								slotsConflicts = true;
								intersection = r1;
								break;
							}
						}
					}
					if(slotsConflicts){
						Warning w = new Warning().addMessage(c1.getName()).addMessage(c2.getName())
								.addMessage(c1.getClassroom().getName()).addMessage(intersection.toString());
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
		for(Class other : stateService.getCurrentState().classes.all()){
			if(other.equals(current)) continue;
			for(Professor p : current.getProfessors()){
				if(other.getProfessors().contains(p) && other.getSlots().contains(query)) return SAME_PROFESSOR;
				
				if(other.getClassroom() == current.getClassroom() && current.getClassroom() != null){
					if(other.getSlots().contains(query)) return SAME_ROOM;
				}
			}
		}
		return NO_CONFLICT;
	}
	
}
