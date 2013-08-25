package schedule.table.models;

import java.util.List;
import java.util.Vector;

import logic.dto.schedule.ScheduleSlot;
import logic.services.NotAllowedWarningsService;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;



public class GroupByProfessorModel extends GeneralGroupModel {
	private static final long serialVersionUID = 3968397630646789006L;
	
	private NotAllowedWarningsService notAllowedService;
	private Professor theProfessor;
	
	public GroupByProfessorModel(List<ScheduleSlot> schedule[][], Professor prof){
		notAllowedService = new NotAllowedWarningsService();
		theProfessor = prof;
		
		configureTable(schedule);
	}
	
	protected boolean solveConflict(List<ScheduleSlot> conflictingScheduleSlots, int row, int column) {
		Vector<Class> classes = getClassesFromConflictingScheduleSlot(conflictingScheduleSlots);
		SlotRange range = SlotRange.singleSlotRange(column - 1, row);
		
		if(!notAllowedService.hasNotAllowedSameProfessorWarnings(classes, theProfessor, range)){
			super.solveConflict(conflictingScheduleSlots, row, column);
			return true;
		}
		return false;
	}

}
