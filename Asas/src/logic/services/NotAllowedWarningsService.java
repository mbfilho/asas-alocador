package logic.services;

import java.util.List;
import java.util.Vector;

import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;
import data.persistentEntities.Warning;
import data.persistentEntities.warningsTypes.SameProfessorsWarning;
import data.persistentEntities.warningsTypes.SameRoomWarning;

public class NotAllowedWarningsService extends WarningGeneratorService{

	
	public boolean hasNotAllowedSameRoomWarnings(List<Class> classes, 
			Classroom room, SlotRange slot){
		return !getNotAllowedSameRoomWarnings(classes, room, slot).isEmpty();
	}
	
	public Vector<SameRoomWarning> getNotAllowedSameRoomWarnings(List<Class> classes, 
			Classroom room, SlotRange slot){
		
		Vector<Warning> allSameRoomWarnings = getSameRoomConflicts(classes);
		Vector<SameRoomWarning> onlyInThisRoomAndSlot = new Vector<SameRoomWarning>();
		for(Warning w : allSameRoomWarnings){
			SameRoomWarning same = (SameRoomWarning) w;
			if(same.getRoom() == room && same.getSlotRange().covers(slot) && !allowedWarningService.isAllowed(same)) 
				onlyInThisRoomAndSlot.add(same);
		}
		
		return onlyInThisRoomAndSlot;
	}
	
	private boolean someRangeCovers(Vector<SlotRange> ranges, SlotRange theRange){
		for(SlotRange r : ranges) if(r.covers(theRange)) return true;
		
		return false;
	}

	public Vector<SameProfessorsWarning> getNotAllowedSameProfessorWarnings(
			Vector<Class> classes, Professor theProfessor, SlotRange range) {
		
		Vector<Warning> allSameProfessorWarnings = getSameProfConflicts(classes);
		Vector<SameProfessorsWarning> onlyForThisProfessorAndSlot = new Vector<SameProfessorsWarning>();
		
		for(Warning w : allSameProfessorWarnings){
			SameProfessorsWarning same = (SameProfessorsWarning) w;
			if(same.getProfessors().contains(theProfessor) 
					&& someRangeCovers(same.getSlots(), range)
					&& !allowedWarningService.isAllowed(same)){
				onlyForThisProfessorAndSlot.add(same);
			}
		}
		
		return onlyForThisProfessorAndSlot;
	}
	
	public boolean hasNotAllowedSameProfessorWarnings(
			Vector<Class> classes, Professor theProfessor, SlotRange range){
		return !getNotAllowedSameProfessorWarnings(classes, theProfessor, range).isEmpty();
	}
}
