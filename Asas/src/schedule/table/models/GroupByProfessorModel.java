package schedule.table.models;

import java.util.Vector;

import basic.Professor;
import basic.SlotRange;

import schedule.ScheduleSlot;
import services.NotAllowedWarningsService;
import basic.Class;

public class GroupByProfessorModel extends GeneralGroupModel {
	private NotAllowedWarningsService notAllowedService;
	private Professor theProfessor;
	
	public GroupByProfessorModel(Vector<ScheduleSlot> schedule[][], Professor prof){
		notAllowedService = new NotAllowedWarningsService();
		theProfessor = prof;
		
		configureTable(schedule);
	}
	
	protected boolean solveConflict(Vector<ScheduleSlot> conflictingScheduleSlots, int row, int column) {
		Vector<Class> classes = getClassesFromConflictingScheduleSlot(conflictingScheduleSlots);
		SlotRange range = SlotRange.singleSlotRange(column - 1, row);
		
		if(!notAllowedService.hasNotAllowedSameProfessorWarnings(classes, theProfessor, range)){
			super.solveConflict(conflictingScheduleSlots, row, column);
			return true;
		}
		return false;
	}

}
