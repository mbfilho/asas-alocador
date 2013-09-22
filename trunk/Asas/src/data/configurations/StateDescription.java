package data.configurations;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class StateDescription implements Serializable{
	private static final long serialVersionUID = 1380469572798348853L;
	private File stateFile;
	private File excelFile;
	private String semester;
	private Date creationTime;
	
	public StateDescription(File excel, String semester){
		creationTime = new Date();
		this.semester = semester;
		excelFile = excel;
		String fileName = creationTime.toString().replace(":", "-").replace(" ", "_");
		stateFile = new File(String.format("savedStates%s%s", File.separator, fileName));
	}
	
	public Date getCreationTime(){
		return creationTime;
	}
	
	public File getExcelFile(){
		return excelFile;
	}
	
	public String getSemester(){
		return semester;
	}
	
	public File getFile(){
		return stateFile;
	}

	public String getFormattedCreationTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(creationTime);
		String months[] = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
		return String.format("%s, %d de %s de %d às %02d:%02d:%02d", 
				getDay(cal.get(Calendar.DAY_OF_WEEK)),
				cal.get(Calendar.DAY_OF_MONTH),
				months[cal.get(Calendar.MONTH)],
				cal.get(Calendar.YEAR),
				cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND)
		       );
	}
	
	private String getDay(int day){
		int nums[] = {Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY};
		String days[] = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"};
		for(int i = 0; i < nums.length; ++i){
			if(day == nums[i]) return days[i];
		}
		
		return null;
	}
}
