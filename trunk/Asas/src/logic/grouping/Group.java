package logic.grouping;

import logic.schedule.Schedule;
import logic.schedule.formatting.formatters.GroupFormatter;
import data.NamedEntity;
import data.persistentEntities.Class;

public class Group implements NamedEntity{
	public Schedule schedule;
	public String groupName;
	
	public Group(String name){
		this(new Schedule(), name);
	}
	
	public Group(Schedule schedule, String name){
		this.schedule = schedule;
		this.groupName = name;
	}

	public String getName() {
		return groupName;
	}
	
	public void addClassToGroup(Class c){
		if(!schedule.containsThisClass(c.getAlias()))
			schedule.addClass(c);
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
	
	public GroupFormatter getFormatter(){
		return new GroupFormatter(schedule);
	}
}
