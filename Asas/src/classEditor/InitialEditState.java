package classEditor;
import groupMaker.InitialFilterConfiguration;
import basic.Class;
import basic.Classroom;
import basic.Professor;
import basic.SlotRange;

public class InitialEditState {
	public Class theClass;
	public Classroom theRoom;
	public int theSemester;
	public Professor theProfessor;
	
	public InitialEditState(Class c, Classroom room, Professor prof){
		theClass = c;
		theRoom = room;
		theSemester = getSemester();
		theProfessor = prof;
	}
	
	public InitialFilterConfiguration deriveInitialFilterConfiguration(){
		InitialFilterConfiguration initial = new InitialFilterConfiguration();
		initial.theRoom = theRoom;
		initial.theSemester = getSemester();
		initial.theProfessor = theProfessor;
		
		return initial;
	}
	
	private int getSemester(){
		if(theClass.getCcSemester() != -1) return theClass.getCcSemester();
		if(theClass.getEcSemester() != -1) return theClass.getEcSemester();
		return -1;
	}
	
}
