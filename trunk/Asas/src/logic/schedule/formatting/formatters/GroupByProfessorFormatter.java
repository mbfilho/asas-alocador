package logic.schedule.formatting.formatters;

import java.util.List;

import logic.schedule.Schedule;
import logic.services.NotAllowedWarningsService;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;

public class GroupByProfessorFormatter extends GroupFormatter{
	private NotAllowedWarningsService notAllowedService;
	private Professor theProfessor;
	
	public GroupByProfessorFormatter(Schedule schedule, Professor prof) {
		super(schedule);
		theProfessor = prof;
		notAllowedService = new NotAllowedWarningsService();
	}
	
	protected boolean isConflictSolved(List<Class> classesInConflict, int slot, int day){
		SlotRange range = SlotRange.singleSlotRange(day, slot);
		
		if(!notAllowedService.hasNotAllowedSameProfessorWarnings(classesInConflict, theProfessor, range))
			return true;
		return false;
	}
}
