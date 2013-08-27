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


import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
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

	private void addToMap(Group group, Class c){
		generatedGroupsSet.add(group);
		generatedGroupsSet.floor(group).addClassToGroup(c);
	}
	
	private void addToMap(List<Group> groupsOfThisClass, Class c){
		for(Group group : groupsOfThisClass){
			addToMap(group, c);
		}
	}
	
	public List<Group> getGroupsDefinedByTheSelector(GroupsSelector selector){
		 generatedGroupsSet.clear();
		 
		for(Class c : classService.all()){
			List<Group> groupsOfThisClass = getGroupsForThisClass(c, selector);
			addToMap(groupsOfThisClass, c);
		}
		
		return new LinkedList<Group>(generatedGroupsSet);
	}
	
	public List<Group> getGroupsForThisClass(Class theClass, GroupsSelector selector){
		List<Group> groups = new LinkedList<Group>();
		if(selector.hasProfessor() &&  theClass.getProfessors().contains(selector.getProfessor())) 
			groups.add(new ProfessorGroup(selector.getProfessor()));
		if(selector.hasSemester() && theClass.getCcSemester() == selector.getSemester()) 
			groups.add(new Group(selector.getSemester() + "(CC)"));
		if(selector.hasSemester() && theClass.getEcSemester() == selector.getSemester()) 
			groups.add(new Group(selector.getSemester() + " (EC)"));
		if(selector.hasClassroom())
			groups.add(new RoomGroup(selector.getClassroom()));
		
		if(selector.generatesNoGroup())
			generateGroupsForEachClassroom(theClass, groups);
		
		return groups;
	}

	private void generateGroupsForEachClassroom(Class theClass, List<Group> groups) {
		HashSet<Classroom> rooms = new HashSet<Classroom>();
		for(SlotRange slot : theClass.getSlots()){
			if(slot.getClassroom() != null && rooms.add(slot.getClassroom())){
				groups.add(new RoomGroup(slot.getClassroom()));
			}
		}
	}
}
