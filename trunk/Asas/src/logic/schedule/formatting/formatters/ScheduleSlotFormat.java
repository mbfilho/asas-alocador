package logic.schedule.formatting.formatters;

import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import utilities.CollectionUtil;

public class ScheduleSlotFormat {
	public Color background, foreground;
	public List<String> content;
	public String tooltip;
	public Border border;
	
	public ScheduleSlotFormat(Color back, Color fore, String cont, String tip){
		this(back, fore, CollectionUtil.createList(cont), tip);
	}
	
	public ScheduleSlotFormat(Color back, Color fore, List<String> cont, String tip){
		background = back;
		foreground = fore;
		content = cont;
		tooltip = tip;
	}
	
	public void setBorder(Color color, int width){
		border = BorderFactory.createLineBorder(color, width);
	}
}
