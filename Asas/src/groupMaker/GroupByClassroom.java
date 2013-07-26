package groupMaker;

import scheduleVisualization.ScheduleSlot;

public class GroupByClassroom extends GenericGroupMaker{

	public String getGroupArg(ScheduleSlot scheduled) {
		return scheduled.theClass.getClassroom().getName(); 
	}

	public boolean canBeGrouped(ScheduleSlot scheduled) {
		return scheduled.theClass.getClassroom() != null;
	}
}
