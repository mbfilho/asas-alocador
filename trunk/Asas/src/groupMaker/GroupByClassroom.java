package groupMaker;

import java.util.HashSet;

import basic.SlotRange;

import scheduleVisualization.ScheduleSlot;

public class GroupByClassroom extends GenericGroupMaker{

	public HashSet<String> getGroupArgs(ScheduleSlot scheduled) {
		HashSet<String> rooms = new HashSet<String>();
		for(SlotRange slot : scheduled.theClass.getSlots()){
			if(slot.getClassroom() != null)
				rooms.add(slot.getClassroom().getName());
		}
		return rooms;
	}

	public boolean canBeGrouped(ScheduleSlot scheduled) {
		for(SlotRange slot : scheduled.theClass.getSlots()){
			if(slot.getClassroom() != null) return true;
		}
		return false;
	}
}
