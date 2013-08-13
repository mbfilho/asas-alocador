package groupMaker;

import basic.Classroom;
import basic.SlotRange;
import scheduleVisualization.Schedule;
import scheduleVisualization.ScheduleSlot;
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
