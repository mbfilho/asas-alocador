package schedule.table.models;

import java.util.Vector;

import logic.dto.schedule.ScheduleSlot;


public class GroupBySemesterModel extends GeneralGroupModel {

	protected boolean solveConflict(Vector<ScheduleSlot> conflictingScheduleSlots, int row, int column){
		//TODO: Do something
		return false;
	}
}
