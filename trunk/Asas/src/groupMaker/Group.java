package groupMaker;

import basic.NamedEntity;
import scheduleVisualization.Schedule;
import scheduleVisualization.ScheduleSlot;
import htmlGenerator.ScheduleTable;

public class Group implements NamedEntity{
	public Schedule schedule;
	public String groupName;
	
	public Group(Schedule schedule, String name){
		this.schedule = schedule;
		this.groupName = name;
	}

	public ScheduleTable generateHtmlTable(String title){
		ScheduleTable table = new ScheduleTable(title + groupName);
		for(int slot = 0; slot < 15; ++slot){
			for(int day = 0; day < 7; ++day){
				String html = "";
				int count =schedule.getSchedule()[slot][day].size(); 
				if(count > 1){
					html += "<table><tr><td class=\"turma\" title = \"Conflito!\" style=\"background-color:red;\">Conflito!</td></tr></table>\n";
				}else if(count == 1){
					ScheduleSlot st = schedule.getSchedule()[slot][day].elementAt(0);
					html += "<table><tr><td class=\"turma\" title = \"" + st.theClass.getName() + "\" style=\"background-color:"+st.theClass.getHtmlColor()+";\">" + st.theClass.getCode() + "</td></tr></table>\n";
				}else{
					html += "<table><tr><td class=\"turma\"></td></tr></table>";
				}
				table.setSlot(slot, day, html);
			}
		}
		return table;
	}

	public String getName() {
		return groupName;
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
}
