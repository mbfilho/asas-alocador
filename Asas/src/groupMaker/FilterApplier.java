package groupMaker;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import scheduleVisualization.Schedule;
import scheduleVisualization.ScheduleSlot;
import services.ClassService;

import basic.Class;
import basic.SlotRange;

public class FilterApplier {

	private ClassService classService;
	private HashMap<String, Schedule> groupsByRoom;
	
	public FilterApplier(){
		classService = new ClassService();
		groupsByRoom = new HashMap<String, Schedule>();
	}

	private void addToMap(SlotRange r, Class c){
		String key = r.getClassroom().getName();
		if(!groupsByRoom.containsKey(key)) groupsByRoom.put(key, new Schedule());
		groupsByRoom.get(key).addScheduleSlot(new ScheduleSlot(c), r);
	}
	
	private void addToMap(Class c){
		for(SlotRange r : c.getSlots()){
			if(r.getClassroom() == null) continue;
			addToMap(r, c);
		}
	}
	
	public Vector<Group> applyFilter(ClassFilter filter){
		 groupsByRoom.clear();
		
		for(Class c : classService.all()){
			if(!filter.isInRole(c)) continue;
			addToMap(c);
		}
		
		Vector<Group> groups = new Vector<Group>(); 
		for(Entry<String, Schedule> pair : groupsByRoom.entrySet())
			groups.add(new Group(pair.getValue(), pair.getKey()));
		
		
		//@TODO isso eh uma gambiarra! 
		if(filter.isFilteringByProfessor()){
			Schedule professorSchedule = new Schedule();
			for(Class c : classService.all()){
				if(!filter.isInRole(c)) continue;
				professorSchedule.addClass(c);
			}
			Group professorSum = new Group(professorSchedule, filter.getProfessor().getName());
			groups.add(professorSum);
		}
		return groups;
	}
}
