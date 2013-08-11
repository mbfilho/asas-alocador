package groupMaker;

import basic.Professor;
import scheduleVisualization.Schedule;

public class ProfessorGroup extends Group{
	public Professor theProfessor;
	
	public ProfessorGroup(Schedule schedule, Professor prof) {
		super(schedule, prof.getName());
		theProfessor = prof;
	}

}
