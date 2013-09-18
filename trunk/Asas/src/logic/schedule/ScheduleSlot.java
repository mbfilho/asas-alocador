package logic.schedule;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import data.persistentEntities.Class;

public class ScheduleSlot {
	private List<Class> classes;
	
	public ScheduleSlot(){
		classes = new LinkedList<Class>();
	}
	
	public void addClass(Class theClass) {
		classes.add(theClass);
	}

	public int getClassesCount() {
		return classes.size();
	}

	public List<Class> getClassesReadOnly() {
		return Collections.unmodifiableList(classes);
	}

	public boolean containsThisClass(Class c) {
		return classes.contains(c);
	}
	
}
