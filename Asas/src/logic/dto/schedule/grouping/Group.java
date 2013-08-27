package logic.dto.schedule.grouping;

import logic.schedule.Schedule;
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
		schedule.addClass(c);
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
}
