package schedule.table.models;

import java.util.List;

import logic.dto.schedule.ScheduleSlot;
import logic.services.NotAllowedWarningsService;

import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;


public class GroupByClassroomModel extends GeneralGroupModel{
	private static final long serialVersionUID = -5498191628270681153L;
	
	private Classroom theRoom;
	private NotAllowedWarningsService notAllowedService;
	
	public GroupByClassroomModel(List<ScheduleSlot> schedule[][], Classroom room){
		theSchedule = schedule;
		theRoom = room;
		notAllowedService = new NotAllowedWarningsService();
		configureTable(schedule);
	}
	
	protected boolean solveConflict(List<ScheduleSlot> theScheduleSlot, int row, int column) {
		List<Class> classes = getClassesFromConflictingScheduleSlot(theScheduleSlot);
		SlotRange range = SlotRange.singleSlotRange(column-1, row);
		
		if(!notAllowedService.hasNotAllowedSameRoomWarnings(classes, theRoom, range)){
			super.solveConflict(theScheduleSlot, row, column);
			return true;
		}
		
		return false;
	}
}
