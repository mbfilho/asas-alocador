package visualizer;

import groupMaker.Group;
import groupMaker.GroupByClassroom;
import groupMaker.GroupByProfessor;
import htmlGenerator.PageWithTables;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import scheduleVisualization.Schedule;
import statePersistence.StateService;
import basic.Class;

public class VisualizerService {
	
	private Schedule createSchedule(){
		Schedule schedule = new Schedule();
		for(Class c : StateService.getInstance().getCurrentState().classes.all()){
			schedule.addClass(c);
		}
		return schedule;
	}
	
	private Vector<Group> sortByName(Vector<Group> groups){
		Collections.sort(groups, new Comparator<Group>() {
			public int compare(Group o1, Group o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return groups;
	}
	
	public Vector<Group> groupByClassroom(){
		return sortByName(new GroupByClassroom().makeGroup(createSchedule()));
	}

	public Vector<Group> groupByProfessor() {
		return sortByName(new GroupByProfessor().makeGroup(createSchedule()));
	}
}
