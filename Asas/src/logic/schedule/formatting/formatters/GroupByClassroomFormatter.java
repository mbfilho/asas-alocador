package logic.schedule.formatting.formatters;

import java.util.List;

import logic.schedule.Schedule;
import logic.services.NotAllowedWarningsService;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;
import data.persistentEntities.Class;

public class GroupByClassroomFormatter extends GroupFormatter{

	private NotAllowedWarningsService notAllowedService;
	private Classroom theRoom;
	
	public GroupByClassroomFormatter(Schedule schedule, Classroom room){
		super(schedule);
		notAllowedService = new NotAllowedWarningsService();
		theRoom = room;
	}
	
	@Override
	protected boolean isConflictSolved(List<Class> conflictingClasses, int slot, int day){
		SlotRange range = SlotRange.singleSlotRange(day, slot);
		
		if(!notAllowedService.hasNotAllowedSameRoomWarnings(conflictingClasses, theRoom, range))
			return true;
		return false;	
	}
}
