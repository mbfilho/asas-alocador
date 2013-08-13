package scheduleTable;

import java.util.Vector;

import scheduleVisualization.ScheduleSlot;

public class GroupBySemesterModel extends GeneralGroupModel {

	protected boolean solveConflict(Vector<ScheduleSlot> conflictingScheduleSlots, int row, int column){
		//TODO: Do something
		return false;
	}
}
