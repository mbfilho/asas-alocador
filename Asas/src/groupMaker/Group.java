package groupMaker;

import basic.NamedEntity;
import scheduleVisualization.Schedule;

public class Group implements NamedEntity{
	public Schedule schedule;
	public String groupName;
	
	public Group(Schedule schedule, String name){
		this.schedule = schedule;
		this.groupName = name;
	}

	public String getName() {
		return groupName;
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
}
