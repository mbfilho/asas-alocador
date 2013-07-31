package services;
import java.util.Vector;

import basic.Class;
import basic.Classroom;
import basic.Professor;
import basic.SlotRange;

public class ConflictService {
	private ClassService classService;
	
	public ConflictService(){
		classService = new  ClassService();
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

	public Vector<Professor> getUnavailableProfessorsOfThisClass(Class theClass, SlotRange range){
		Vector<Professor> profs = new Vector<Professor>();
		
outer:  for(Professor prof : theClass.getProfessors()){
			for(Class c : classService.all()){
				if(c.getId() == theClass.getId()) continue;
				if(!c.getProfessors().contains(prof)) continue;
				
				for(SlotRange r : c.getSlots())	if(r.intersects(range)){
					profs.add(prof);
					continue outer;
				}
			}
		}
		
		return profs;	
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

	public Vector<Class> getClassesOccupingThisRoom(SlotRange range) {
		Vector<Class> classes = new Vector<Class>();
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
