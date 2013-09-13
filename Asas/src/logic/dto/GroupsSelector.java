package logic.dto;


import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;

public class GroupsSelector {

	private int semester;
	private Professor professor;
	private Classroom classroom;
	
	private boolean hasSemester, hasProfessor, hasClassroom;
	
	public GroupsSelector(){
		semester = -1;
	}
	
	public void setSemester(String semester) {
		this.semester = semester == null ? -1 : Integer.parseInt(semester);
		hasSemester = true;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
		hasProfessor = true;
	}

	public Professor getProfessor(){
		return this.professor; 
	}
	
	public void setRoom(Classroom theRoom) {
		this.classroom = theRoom;
		hasClassroom = true;
	}
	
	public boolean generatesNoGroup(){
		return !hasProfessor && !hasSemester && !hasClassroom;
	}

	public boolean groupByOneProfessor() {
		return professor != null;
	}
	
	public boolean groupByAllProfessors(){
		return hasProfessor && !groupByOneProfessor();
	}

	public boolean groupByOneSemester() {
		return semester != -1;
	}
	
	public boolean groupByAllSemesters(){
		return hasSemester && !groupByOneSemester();
	}

	public int getSemester() {
		return semester;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public boolean groupByOneClassroom() {
		return classroom != null;
	}
	
	public boolean groupByAllClassrooms(){
		return hasClassroom && !groupByOneClassroom();
	}
}
