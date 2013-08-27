package schedule.table.models;

import java.util.List;

import logic.schedule.Schedule;
import logic.services.NotAllowedWarningsService;

import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;


public class GroupByClassroomModel extends GeneralGroupModel{
	private static final long serialVersionUID = -5498191628270681153L;
	
	private Classroom theRoom;
	private NotAllowedWarningsService notAllowedService;
	
	public GroupByClassroomModel(Schedule schedule, Classroom room){
		theSchedule = schedule;
		theRoom = room;
		notAllowedService = new NotAllowedWarningsService();
		configureTable(schedule);
	}
	
	protected boolean solveConflict(List<Class> conflictingClasses, int row, int column) {
		SlotRange range = SlotRange.singleSlotRange(column-1, row);
		
		if(!notAllowedService.hasNotAllowedSameRoomWarnings(conflictingClasses, theRoom, range)){
			super.solveConflict(conflictingClasses, row, column);
			return true;
		}
		
		return false;
	}
}
