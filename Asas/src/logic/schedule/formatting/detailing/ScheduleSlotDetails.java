package logic.schedule.formatting.detailing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ScheduleSlotDetails implements Iterable<Details>{
	private List<Details> blockDetailed;
	
	public ScheduleSlotDetails(){
		blockDetailed = new LinkedList<Details>();
	}
	
	public void add(Details details){
		blockDetailed.add(details);
	}
	
	public Iterator<Details> iterator() {
		return blockDetailed.iterator();
	}

}
