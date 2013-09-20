package presentation.classes;
import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;

public class InitialEditState {
	public Class theClass;
	public Classroom theRoom;
	public int theSemester;
	public Professor theProfessor;
	public boolean openAdditionalWindows;
	
	public InitialEditState(Class c){
		openAdditionalWindows = false;
		theClass = c;
	}
	
	public InitialEditState(Class c, Classroom room, Professor prof){
		theClass = c;
		theRoom = room;
		theSemester = getSemester();
		theProfessor = prof;
		openAdditionalWindows = true;
	}
	
	private int getSemester(){
		if(theClass.getCcSemester() != -1) return theClass.getCcSemester();
		if(theClass.getEcSemester() != -1) return theClass.getEcSemester();
		return -1;
	}

	public boolean openAdditionalWindows() {
		return openAdditionalWindows;
	}
	
}
