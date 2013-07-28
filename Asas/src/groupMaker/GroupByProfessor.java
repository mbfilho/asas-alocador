package groupMaker;

import java.util.HashSet;

import basic.Professor;

import scheduleVisualization.ScheduleSlot;

public class GroupByProfessor extends GenericGroupMaker {

	public boolean canBeGrouped(ScheduleSlot scheduled) {
		return !scheduled.theClass.getProfessors().isEmpty();
	}

	public HashSet<String> getGroupArgs(ScheduleSlot scheduled) {
		HashSet<String> professorNames = new HashSet<String>();
		for(Professor p : scheduled.theClass.getProfessors()) professorNames.add(p.getName());
		
		return professorNames;
	}

}
