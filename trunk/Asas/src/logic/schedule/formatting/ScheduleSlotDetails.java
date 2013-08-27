package logic.schedule.formatting;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ScheduleSlotDetails implements Iterable<ClassDetails>{
	private List<ClassDetails> classesDetailed;
	
	public ScheduleSlotDetails(){
		classesDetailed = new LinkedList<ClassDetails>();
	}
	
	public void add(ClassDetails classDetailed){
		classesDetailed.add(classDetailed);
	}
	
	public Iterator<ClassDetails> iterator() {
		return classesDetailed.iterator();
	}

}
