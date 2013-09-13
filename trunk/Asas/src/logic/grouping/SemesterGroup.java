package logic.grouping;

import logic.schedule.Schedule;

public class SemesterGroup extends Group {
	
	private static String generateName(int semester, boolean isCC){
		String course = isCC ? "CC" : "EC";
		return String.format("%d (%s)", semester, course);
	}
	
	public SemesterGroup(Schedule schedule, int semester, boolean cc) {
		super(schedule, generateName(semester, cc));
	}
	
	public SemesterGroup(int semester, boolean cc){
		this(new Schedule(), semester, cc);
	}
	

}
