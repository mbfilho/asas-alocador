package logic.dto;


import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;

public class GroupsSelector {

	private int semester;
	private Professor professor;
	private Classroom classroom;
	private String area;
	
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
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public void setRoom(Classroom theRoom) {
		this.classroom = theRoom;
		hasClassroom = true;
	}
	
	public boolean generatesNoGroup(){
		return !hasProfessor() && !hasSemester() && !hasClassroom();
	}

	public boolean hasProfessor() {
		return hasProfessor;
	}

	public boolean hasSemester() {
		return hasSemester;
	}

	public int getSemester() {
		return semester;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public boolean hasClassroom() {
		return hasClassroom;
	}
}
