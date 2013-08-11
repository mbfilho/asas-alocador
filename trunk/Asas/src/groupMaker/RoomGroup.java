package groupMaker;

import basic.Classroom;
import scheduleVisualization.Schedule;

public class RoomGroup extends Group{
	public Classroom theRoom;
	
	public RoomGroup(Schedule schedule, Classroom room) {
		super(schedule, room.getName());
		theRoom = room;
	}

}
