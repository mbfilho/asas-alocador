package logic.dto;


import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;

public class GroupsSelector {

	private int semester;
	private Professor professor;
	private Classroom classroom;
	private String area;
	
	public GroupsSelector(){
		semester = -1;
	}
	
	public void setSemester(String semester) {
		this.semester = semester == null ? -1 : Integer.parseInt(semester);
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Professor getProfessor(){
		return this.professor; 
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public void setRoom(Classroom theRoom) {
		this.classroom = theRoom;
	}
	
	public boolean generatesNoGroup(){
		return !hasProfessor() && !hasSemester() && !hasClassroom();
	}

	public boolean hasProfessor() {
		return professor != null;
	}

	public boolean hasSemester() {
		return semester != -1;
	}

	public int getSemester() {
		return semester;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public boolean hasClassroom() {
		return classroom != null;
	}
}
