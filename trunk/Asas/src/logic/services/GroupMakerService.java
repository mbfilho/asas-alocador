package logic.services;


import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import logic.dto.GroupsSelector;
import logic.grouping.Group;
import logic.grouping.ProfessorGroup;
import logic.grouping.RoomGroup;
import logic.grouping.SemesterGroup;


import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;

public class GroupMakerService {

	private ClassService classService;
	private TreeSet<Group> generatedGroupsSet;
	
	public GroupMakerService(){
		classService = new ClassService();
		generatedGroupsSet = new TreeSet<Group>(new Comparator<Group>() {
			public int compare(Group g1, Group g2) {
				return g1.getName().compareTo(g2.getName());
			}
		});
	}

	public List<Group> getGroupsDefinedByTheSelector(GroupsSelector selector){
		 generatedGroupsSet.clear();
		 
		for(Class c : classService.all()){
			List<Group> groupsOfThisClass = getEmptyGroupsForThisClass(c, selector);
			addToMap(groupsOfThisClass, c);
		}
		
		return new LinkedList<Group>(generatedGroupsSet);
	}
	
	private void addToMap(List<Group> groupsOfThisClass, Class c){
		for(Group group : groupsOfThisClass)
			addToMap(group, c);
	}
	
	private void addToMap(Group group, Class c){
		generatedGroupsSet.add(group);
		generatedGroupsSet.floor(group).addClassToGroup(c);
	}
	
	private List<Group> getEmptyGroupsForThisClass(Class theClass, GroupsSelector selector){
		List<Group> groups = new LinkedList<Group>();
		
		groups.addAll(generateProfessorGroups(theClass, selector));
		groups.addAll(generateSemesterGroups(theClass, selector));
		groups.addAll(generateRoomGroups(theClass, selector));
		
		if(selector.generatesNoGroup())
			generateGroupsForEachClassroom(theClass, groups);
		
		return groups;
	}

	private List<Group> generateRoomGroups(Class theClass, GroupsSelector selector) {
		List<Group> groups = new LinkedList<Group>();
		
		if(selector.groupByOneClassroom() && theClass.getAllRooms().contains(selector.getClassroom()))
			groups.add(new RoomGroup(selector.getClassroom()));
		else if(selector.groupByAllClassrooms())
			generateGroupsForEachClassroom(theClass, groups);
		
		return groups;
	}

	private List<Group> generateSemesterGroups(Class theClass,GroupsSelector selector) {
		List<Group> groups = new LinkedList<Group>();
		
		if(selector.groupByOneSemester()){
			if(theClass.getCcSemester() == selector.getSemester())
				groups.add(new SemesterGroup(selector.getSemester(), true));
			if(theClass.getEcSemester() == selector.getSemester()) 
				groups.add(new SemesterGroup(selector.getSemester(), false));
		}else if(selector.groupByAllSemesters())
			generateGroupsForEachSemester(theClass, groups);
		
		return groups;
	}

	private List<Group> generateProfessorGroups(Class theClass, GroupsSelector selector) {
		List<Group> groups = new LinkedList<Group>();
		
		if(selector.groupByOneProfessor() && theClass.getProfessors().contains(selector.getProfessor()))
			groups.add(new ProfessorGroup(selector.getProfessor()));
		else if(selector.groupByAllProfessors())
			generateGroupsForEachProfessor(theClass, groups);
		
		return groups;
	}

	private void generateGroupsForEachSemester(Class theClass, List<Group> groups) {
		if(theClass.getCcSemester() != -1)
			groups.add(new SemesterGroup(theClass.getCcSemester(), true));
		if(theClass.getEcSemester() != -1)
			groups.add(new SemesterGroup(theClass.getEcSemester(), false));
	}

	private void generateGroupsForEachClassroom(Class theClass, List<Group> groups) {
		HashSet<Classroom> rooms = new HashSet<Classroom>();
		for(SlotRange slot : theClass.getSlots()){
			if(slot.getClassroom() != null && !slot.getClassroom().isExternal() && rooms.add(slot.getClassroom())){
				groups.add(new RoomGroup(slot.getClassroom()));
			}
		}
	}
	
	private void generateGroupsForEachProfessor(Class theClass, List<Group> groups){
		for(Professor prof : theClass.getProfessors())
			groups.add(new ProfessorGroup(prof));
	}
}
