package group;

import basic.Professor;
import schedule.Schedule;

public class ProfessorGroup extends Group{
	public Professor theProfessor;
	
	public ProfessorGroup(Schedule schedule, Professor prof) {
		super(schedule, prof.getName());
		theProfessor = prof;
	}
	
	public ProfessorGroup(Professor prof){
		this(new Schedule(), prof);
	}

}
