package group.filtering;

import group.Group;
import group.ProfessorGroup;
import group.RoomGroup;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import basic.Class;
import basic.Classroom;
import basic.Professor;
import basic.SlotRange;

public class ClassFilter {

	private int semester;
	private Professor professor;
	private Classroom classroom;
	private String area;
	
	public ClassFilter(){
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
	
	public boolean isEmptyFilter(){
		return professor == null && semester == -1 && classroom == null;
	}
	
	/**
	 * 
	 * @param thisClass - a turma a ser filtrada (agrupada)
	 * @return Lista dos grupos aos quais essa turma pertence
	 */
	public List<Group> getGroupsOfThisClass(Class thisClass){
		List<Group> groups = new LinkedList<Group>();
		if(professor != null &&  thisClass.getProfessors().contains(professor)) 
			groups.add(new ProfessorGroup(professor));
		if(semester != -1 && thisClass.getCcSemester() == semester) 
			groups.add(new Group(semester + "(CC)"));
		if(semester != -1 && thisClass.getEcSemester() == semester) 
			groups.add(new Group(semester + " (EC)"));
		if(classroom != null)
			groups.add(new RoomGroup(classroom));
		
		if(isEmptyFilter()){
			HashSet<Classroom> rooms = new HashSet<Classroom>();
			for(SlotRange slot : thisClass.getSlots()){
				if(slot.getClassroom() != null && rooms.add(slot.getClassroom())){
					groups.add(new RoomGroup(slot.getClassroom()));
				}
			}
		}
		
		return groups;
	}
}
