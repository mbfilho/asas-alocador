package group;

import basic.Classroom;
import basic.SlotRange;
import schedule.Schedule;
import schedule.ScheduleSlot;
import basic.Class;

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
				schedule.addScheduleSlot(new ScheduleSlot(c), slot);
		}
	}
}
