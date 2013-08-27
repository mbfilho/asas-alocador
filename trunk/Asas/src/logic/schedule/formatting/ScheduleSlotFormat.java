package logic.schedule.formatting;

import java.awt.Color;

public class ScheduleSlotFormat {
	public Color background, foreground;
	public String content, tooltip;
	
	public ScheduleSlotFormat(Color back, Color fore, String cont, String tip){
		background = back;
		foreground = fore;
		content = cont;
		tooltip = tip;
	}
}
