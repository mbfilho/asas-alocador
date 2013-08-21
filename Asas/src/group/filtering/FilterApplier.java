package group.filtering;

import group.Group;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import services.ClassService;

import basic.Class;

public class FilterApplier {

	private ClassService classService;
	private TreeSet<Group> generatedGroupsSet;
	
	public FilterApplier(){
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
	
	public List<Group> applyFilter(ClassFilter filter){
		 generatedGroupsSet.clear();
		 
		for(Class c : classService.all()){
			List<Group> groupsOfThisClass = filter.getGroupsOfThisClass(c);
			addToMap(groupsOfThisClass, c);
		}
		
		return new LinkedList<Group>(generatedGroupsSet);
	}
}
