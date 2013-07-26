package visualizer;

import groupMaker.Group;
import groupMaker.GroupByClassroom;
import htmlGenerator.PageWithTables;

import java.util.Vector;

import scheduleVisualization.Schedule;
import statePersistence.StateService;
import basic.Class;

public class VisualizerService {
	
	public Vector<Group> groupByClassroom(){
		Schedule schedule = new Schedule();
		for(Class c : StateService.getInstance().getCurrentState().classes.all()){
			schedule.addClass(c);
		}
		return new GroupByClassroom().makeGroup(schedule);
	}
}
