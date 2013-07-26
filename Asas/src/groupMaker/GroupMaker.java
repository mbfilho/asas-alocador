package groupMaker;

import java.util.Vector;

import scheduleVisualization.Schedule;

public interface GroupMaker {
	Vector<Group> makeGroup(Schedule schedule);
}
