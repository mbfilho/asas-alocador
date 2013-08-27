package logic.schedule;

import java.util.List;

import data.persistentEntities.Class;
import data.persistentEntities.SlotRange;

import utilities.Constants;

public class Schedule {
	private ScheduleSlot schedule[][];
	
	public Schedule(){
		schedule = new ScheduleSlot[Constants.SLOTS][Constants.DAYS];
		for(int i = 0; i < schedule.length; ++i) for(int j = 0; j < Constants.DAYS; ++j)
			schedule[i][j] = new ScheduleSlot();
	}
	
	public void addClassToThisRange(Class theClass, SlotRange range){
		for(int i = range.getStartSlot(); i <= range.getEndSlot(); ++i) 
			addClassToSingleSlot(theClass, i, range.getDay());
	}
		
	public void addClassToSingleSlot(Class theClass, int slot, int day){
		schedule[slot][day].addClass(theClass);
	}
	
	public void addClass(Class theClass){
		for(SlotRange range : theClass.getSlots())
			addClassToThisRange(theClass, range);
	}

	public boolean hasConflict(int slot, int day) {
		return schedule[slot][day].getClassesCount() > 1;
	}

	public List<Class> getClassesForRead(int slot, int day) {
		return schedule[slot][day].getClassesForRead();
	}

	public boolean isEmptySlot(int slot, int day) {
		return schedule[slot][day].getClassesCount() == 0;
	}

	public Class getSingleClass(int slot, int day) {
		return schedule[slot][day].getClassesForRead().get(0);
	}
}
