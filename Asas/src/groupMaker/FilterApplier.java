package groupMaker;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import scheduleVisualization.Schedule;
import scheduleVisualization.ScheduleSlot;
import services.ClassService;

import basic.Class;
import basic.Classroom;
import basic.SlotRange;

public class FilterApplier {

	private ClassService classService;
	private HashMap<Classroom, Schedule> groupsByRoom;
	
	public FilterApplier(){
		classService = new ClassService();
		groupsByRoom = new HashMap<Classroom, Schedule>();
	}

	private void addToMap(SlotRange r, Class c){
		Classroom key = r.getClassroom();
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
		for(Entry<Classroom, Schedule> pair : groupsByRoom.entrySet())
			groups.add(new RoomGroup(pair.getValue(), pair.getKey()));
		
		
		//@TODO isso eh uma gambiarra! 
		if(filter.isFilteringByProfessor()){
			Schedule professorSchedule = new Schedule();
			for(Class c : classService.all()){
				if(!filter.isInRole(c)) continue;
				professorSchedule.addClass(c);
			}
			ProfessorGroup professorSum = new ProfessorGroup(professorSchedule, filter.getProfessor());
			groups.add(professorSum);
		}
		return groups;
	}
}
