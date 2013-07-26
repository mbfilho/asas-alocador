package visualizer;

import htmlGenerator.PageWithTables;

import java.util.Vector;

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
