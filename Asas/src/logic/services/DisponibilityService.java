package logic.services;
import java.util.LinkedList;
import java.util.List;

import logic.dto.ProfessorIndisponibility;

import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;


public class DisponibilityService {
	private ClassService classService;
	
	public DisponibilityService(){
		classService = ClassService.createServiceFromCurrentState();
	}
	
	public boolean isClassroomFreeForThisClass(Class thisClass, Classroom room, SlotRange range){
		for(Class c : classService.all()){
			if(c.getId() == thisClass.getId()) continue;
			for(SlotRange s : c.getSlots()){
				if(s.intersects(range) &&  s.getClassroom() == room)
					return false;
			}
		}
		return true;
	}

	public List<ProfessorIndisponibility> getUnavailableProfessorsOfThisClass(Class theClass, SlotRange range){
		List<ProfessorIndisponibility> profsAndClass = new LinkedList<ProfessorIndisponibility>();
		
		for(Professor prof : theClass.getProfessors()){
			for(Class c : classService.all()){
				if(c.getId() == theClass.getId()) continue;
				if(!c.getProfessors().contains(prof)) continue;
				
				for(SlotRange r : c.getSlots())	if(r.intersects(range)){
					SlotRange slot = range.clone();
					slot.setClassroom(r.getClassroom());
					profsAndClass.add(new ProfessorIndisponibility(prof, c, slot));
					break;
				}
			}
		}
		
		return profsAndClass;	
	}
	
	public boolean areProfessorsOfThisClassAvailable(Class theClass, SlotRange range) {
		return getUnavailableProfessorsOfThisClass(theClass, range).isEmpty();
	}

	public boolean isClassInThisRoomAtThisSlot(Class theClass, SlotRange slotRange) {
		for(SlotRange r : theClass.getSlots()){
			if(r.getClassroom() == slotRange.getClassroom() && slotRange.intersects(r)) return true;
		}
		return false;
	}

	public List<Class> getClassesOccupingThisRoom(SlotRange range) {
		List<Class> classes = new LinkedList<Class>();
		for(Class c : classService.all()){
			for(SlotRange r : c.getSlots()){
				if(r.getClassroom() == range.getClassroom() && range.intersects(r)){
					classes.add(c);
					break;
				}
					
			}
		}
		return classes;
	}
}
