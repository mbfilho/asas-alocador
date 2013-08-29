package logic.grouping;

import logic.schedule.Schedule;
import logic.schedule.formatting.formatters.GroupByClassroomFormatter;
import logic.schedule.formatting.formatters.GroupFormatter;
import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.SlotRange;

public class RoomGroup extends Group{
	public Classroom theRoom;
	
	public RoomGroup(Schedule schedule, Classroom room) {
		super(schedule, room.getName());
		theRoom = room;
	}
	
	public RoomGroup(Classroom room){
		this(new Schedule(), room);
	}

	public void addClassToGroup(Class c){
		for(SlotRange slot : c.getSlots()){
			if(slot.getClassroom() == theRoom)
				schedule.addClassToThisRange(c, slot);
		}
	}
	
	public GroupFormatter getFormatter(){
		return new GroupByClassroomFormatter(schedule, theRoom);
	}
}
