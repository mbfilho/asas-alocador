package scheduleTable;

import java.util.Vector;

import scheduleVisualization.ScheduleSlot;
import services.NotAllowedWarningsService;
import basic.Classroom;
import basic.Class;
import basic.SlotRange;

public class GroupByClassroomModel extends GeneralGroupModel{
	
	private Classroom theRoom;
	private NotAllowedWarningsService notAllowedService;
	
	public GroupByClassroomModel(Vector<ScheduleSlot> schedule[][], Classroom room){
		theRoom = room;
		notAllowedService = new NotAllowedWarningsService();
		configureTable(schedule);
	}
	
	protected void solveConflict(Vector<ScheduleSlot> theScheduleSlot, int row, int column) {
		Vector<Class> classes = getClassesFromConflictingScheduleSlot(theScheduleSlot);
		SlotRange range = SlotRange.singleSlotRange(column-1, row);
		
		if(!notAllowedService.hasNotAllowedSameRoomWarnings(classes, theRoom, range))
			super.solveConflict(theScheduleSlot, row, column);
	}
}
