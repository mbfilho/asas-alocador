package logic.grouping;

import logic.schedule.Schedule;
import logic.schedule.formatting.formatters.GroupByProfessorFormatter;
import logic.schedule.formatting.formatters.GroupFormatter;
import data.persistentEntities.Professor;

public class ProfessorGroup extends Group{
	public Professor theProfessor;
	
	public ProfessorGroup(Schedule schedule, Professor prof) {
		super(schedule, prof.getName());
		theProfessor = prof;
	}
	
	public ProfessorGroup(Professor prof){
		this(new Schedule(), prof);
	}

	public GroupFormatter getFormatter(){
		return new GroupByProfessorFormatter(schedule, theProfessor);
	}
}
