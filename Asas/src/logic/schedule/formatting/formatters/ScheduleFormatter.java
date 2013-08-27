package logic.schedule.formatting.formatters;

import logic.schedule.formatting.detailing.ScheduleSlotDetails;

public interface ScheduleFormatter {

	public ScheduleSlotFormat getFormat(int slot, int day);
	public ScheduleSlotDetails getDetails(int slot, int day);
}
