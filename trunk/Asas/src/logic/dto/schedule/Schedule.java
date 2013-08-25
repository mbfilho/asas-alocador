package logic.dto.schedule;

import java.util.LinkedList;
import java.util.List;

import data.persistentEntities.Class;
import data.persistentEntities.SlotRange;

import utilities.Constants;

public class Schedule {
	private List<ScheduleSlot> schedule[][];
	
	@SuppressWarnings("unchecked")
	public Schedule(){
		schedule = new LinkedList[Constants.SLOTS][Constants.DAYS];
		for(int i = 0; i < schedule.length; ++i) for(int j = 0; j < Constants.DAYS; ++j)
			schedule[i][j] = new LinkedList<ScheduleSlot>();
	}
	
	public List<ScheduleSlot>[][] getSchedule(){
		return schedule;
	}
	
	public void addScheduleSlot(ScheduleSlot scheduled, SlotRange range){
		for(int i = range.getStartSlot(); i <= range.getEndSlot(); ++i) addScheduleSlot(scheduled, i, range.getDay());
	}
		
	public void addScheduleSlot(ScheduleSlot scheduled, int slot, int day){
		schedule[slot][day].add(scheduled);
	}
	
	public void addClass(Class theClass){
		ScheduleSlot scheduleSlot = new ScheduleSlot(theClass);
		
		for(SlotRange range : theClass.getSlots()){
			for(int slot = range.getStartSlot(); slot <= range.getEndSlot(); ++slot)
				addScheduleSlot(scheduleSlot, slot, range.getDay());
		}
	}
}
