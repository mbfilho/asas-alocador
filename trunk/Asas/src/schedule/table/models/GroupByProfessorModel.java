package schedule.table.models;

import java.util.List;

import logic.schedule.Schedule;
import logic.services.NotAllowedWarningsService;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;



public class GroupByProfessorModel extends GeneralGroupModel {
	private static final long serialVersionUID = 3968397630646789006L;
	
	private NotAllowedWarningsService notAllowedService;
	private Professor theProfessor;
	
	public GroupByProfessorModel(Schedule schedule, Professor prof){
		notAllowedService = new NotAllowedWarningsService();
		theProfessor = prof;
		
		configureTable(schedule);
	}
	
	protected boolean solveConflict(List<Class> conflictingClasses, int row, int column) {
		SlotRange range = SlotRange.singleSlotRange(column - 1, row);
		
		if(!notAllowedService.hasNotAllowedSameProfessorWarnings(conflictingClasses, theProfessor, range)){
			super.solveConflict(conflictingClasses, row, column);
			return true;
		}
		return false;
	}

}
