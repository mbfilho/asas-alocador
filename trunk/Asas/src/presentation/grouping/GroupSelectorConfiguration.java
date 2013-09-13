package presentation.grouping;

import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;

public class GroupSelectorConfiguration {
	private static int PROFESSOR = 1 << 0, SEMESTER = 1 << 2, ROOM = 1 << 3;
	private static int ALL = PROFESSOR | SEMESTER | ROOM;
	private static int NONE = 0;

	private Professor theProfessor;
	private Classroom theRoom;
	private int theSemester;
	private int configuration;
	
	public static GroupSelectorConfiguration onlyProfessor(Professor prof){
		GroupSelectorConfiguration config = new GroupSelectorConfiguration();
		config.enableProfessorGrouping(prof);
		return config;
	}
	
	public static GroupSelectorConfiguration onlyRoom(Classroom room){
		GroupSelectorConfiguration config = new GroupSelectorConfiguration();
		config.enableRoomGrouping(room);
		return config;
	}	
	
	public static GroupSelectorConfiguration onlySemester(int semester){
		GroupSelectorConfiguration config = new GroupSelectorConfiguration();
		config.enableSemesterGrouping(semester);
		return config;
	}
	
	public static GroupSelectorConfiguration configurationForFullSelector(){
		GroupSelectorConfiguration config = new GroupSelectorConfiguration();
		config.enableAllGroupings();
		return config;
	}
	
	private GroupSelectorConfiguration(){
		configuration = NONE;
		theSemester = -1;
	}
	
	public void enableAllGroupings(){
		configuration = ALL;
	}
	
	public void enableProfessorGrouping(Professor prof){
		theProfessor = prof;
		configuration |= PROFESSOR;
	}
	
	public boolean isProfessorGroupingEnabled(){
		return (configuration & PROFESSOR) != 0;
	}
	
	public void enableRoomGrouping(Classroom room){
		theRoom = room;
		configuration |= ROOM;
	}
	
	public boolean isRoomGroupingEnabled(){
		return (configuration & ROOM) != 0;
	}
	
	public void enableSemesterGrouping(int semester){
		theSemester = semester;
		configuration |= SEMESTER;
	}
	
	public boolean isSemesterGroupingEnabled(){
		return (configuration & SEMESTER) != 0;
	}

	public Professor getProfessor() {
		return theProfessor;
	}

	public Classroom getRoom() {
		return theRoom;
	}

	public int getSemester() {
		return theSemester;
	}
}
