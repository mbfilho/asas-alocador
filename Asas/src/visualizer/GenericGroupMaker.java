package visualizer;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

public abstract class GenericGroupMaker implements GroupMaker {

	public abstract boolean canBeGrouped(ScheduleSlot scheduled);
	public abstract String getGroupArg(ScheduleSlot scheduled);
	
	public Vector<Group> makeGroup(Schedule schedule) {
		HashMap<String, Schedule> map = new HashMap<String, Schedule>();
		Vector<ScheduleSlot> week[][] = schedule.getSchedule();
		for(int slot = 0; slot < week.length; ++slot){
			for(int day = 0; day < 7; ++day){
				for(ScheduleSlot scheduled : week[slot][day]){
					if(!canBeGrouped(scheduled)) continue;
					String name = getGroupArg(scheduled);
					
					if(!map.containsKey(name))
						map.put(name, new Schedule());
					map.get(name).addScheduleSlot(scheduled, slot, day);
				}
			}
		}
		Vector<Group> grouped = new Vector<Group>();
		for(Entry<String,Schedule> entry : map.entrySet())
			grouped.add(new Group(entry.getValue(), entry.getKey()));
		
		return grouped;
	}

}
