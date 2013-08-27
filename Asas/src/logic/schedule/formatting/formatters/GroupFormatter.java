package logic.schedule.formatting.formatters;

import java.awt.Color;
import java.util.List;

import data.persistentEntities.Class;

import logic.schedule.Schedule;
import logic.schedule.formatting.detailing.DetailsOfClass;
import logic.schedule.formatting.detailing.ScheduleSlotDetails;

public class GroupFormatter implements ScheduleFormatter{
	
	private Schedule schedule;

	public GroupFormatter(Schedule schedule){
		this.schedule = schedule;
	}
	
	public ScheduleSlotFormat getFormat(int slot, int day){
		if(schedule.hasConflict(slot, day)){
			if(isConflictSolved(schedule.getClassesForRead(slot, day), slot, day)){
				return new ScheduleSlotFormat(Color.yellow, Color.black, "Conflitos autorizados.", "");
			}else{
				return new ScheduleSlotFormat(Color.red, Color.white, "Conflito. Clique aqui.", "");
			}
		}else if(!schedule.isEmptySlot(slot, day)){
			Class theClass = schedule.getSingleClass(slot, day);
			return new ScheduleSlotFormat(theClass.getColor(), Color.black, theClass.completeName(), theClass.completeName());
		}else{
			return new ScheduleSlotFormat(Color.white, Color.black, "", "");
		}
	}
	
	protected boolean isConflictSolved(List<Class> classesInConflict, int slot, int day){
		return false;
	}
	
	public ScheduleSlotDetails getDetails(int slot, int day){
		ScheduleSlotDetails details = new ScheduleSlotDetails();
		
		if(schedule.hasConflict(slot, day)){
			for(Class inConflict : schedule.getClassesForRead(slot, day))
				details.add(new DetailsOfClass(inConflict));
		}else if(!schedule.isEmptySlot(slot, day)){
			Class single = schedule.getSingleClass(slot, day);
			details.add(new DetailsOfClass(single));
		}
		
		return details;
	}
}
