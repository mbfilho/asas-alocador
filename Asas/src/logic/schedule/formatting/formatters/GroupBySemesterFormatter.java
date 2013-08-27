package logic.schedule.formatting.formatters;

import java.util.List;

import logic.schedule.Schedule;

import data.persistentEntities.Class;

public class GroupBySemesterFormatter extends GroupFormatter{

	public GroupBySemesterFormatter(Schedule schedule){
		super(schedule);
	}
	
	protected boolean isConflictSolved(List<Class> classesInConflict, int slot, int day){
		//TODO: do something
		return false;
	}
	
}
