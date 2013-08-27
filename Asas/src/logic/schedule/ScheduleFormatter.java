package logic.schedule;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.persistentEntities.Class;

import logic.schedule.formatting.ClassDetails;
import logic.schedule.formatting.ScheduleSlotDetails;
import logic.schedule.formatting.ScheduleSlotFormat;

public class ScheduleFormatter {
	
	private Schedule schedule;

	public ScheduleSlotFormat getFormat(int slot, int day){
		if(schedule.hasConflict(slot, day)){
			if(isConflictSolved(slot, day)){
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
	
	protected boolean isConflictSolved(int slot, int day){
		return false;
	}
	
	/*
	 * if(theSchedule.hasConflict(slot, day)){
			panel = new JPanel(new GridLayout());
			panel.add(new JLabel("Turmas conflitantes:"));
			
			for(Class conflicted: theSchedule.getClassesForRead(slot, day))
				addClassInfoToPanel(conflicted, panel);
			
		}else if(!theSchedule.isEmptySlot(slot, day)){
			Class scheduled = theSchedule.getSingleClass(slot, day);
			panel = new JPanel(new GridLayout());
			addClassInfoToPanel(scheduled, panel);
		}
		
	 */
	public ScheduleSlotDetails getDetails(int slot, int day){
		if(schedule.hasConflict(slot, day)){
			ScheduleSlotDetails details = new ScheduleSlotDetails();
			for(Class inConflict : schedule.getClassesForRead(slot, day)){
				details.add(new ClassDetails(inConflict));
			}
		}else{
			
		}
		return null;
	}
}
