package services;

import java.util.Vector;

import basic.Classroom;
import basic.Class;
import basic.Professor;
import basic.SlotRange;

import warnings.types.SameProfessorsWarning;
import warnings.types.SameRoomWarning;
import warnings.types.Warning;

public class NotAllowedWarningsService extends WarningGeneratorService{

	
	public boolean hasNotAllowedSameRoomWarnings(Vector<Class> classes, 
			Classroom room, SlotRange slot){
		return !getNotAllowedSameRoomWarnings(classes, room, slot).isEmpty();
	}
	
	public Vector<SameRoomWarning> getNotAllowedSameRoomWarnings(Vector<Class> classes, 
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
