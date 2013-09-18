package data.persistentEntities.warningsTypes;

import java.util.LinkedList;
import java.util.List;

import presentation.NamedPair;
import presentation.classes.InitialEditState;

import data.persistentEntities.Class;
import data.persistentEntities.Classroom;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;
import data.persistentEntities.Warning;

import utilities.CollectionUtil;
import utilities.StringUtil;


public class SameProfessorsWarning extends Warning{

	private static final long serialVersionUID = -151872803891658353L;
	
	private Class oneClass, otherClass;
	private List<Professor> professors;
	private List<SlotRange> slots;
	
	public SameProfessorsWarning(Class c1, Class c2, List<Professor> profs, List<SlotRange> ranges){
		oneClass = c1;
		otherClass = c2;
		professors = profs;
		slots = ranges;
	}
	
	public String getMessage() {
		return String.format("<html>Professor(es) <b>%s</b> nas turmas <b>%s<i>%s</i></b> e <b>%s<i>%s</i></b>, <b>%s</b></html>",
					StringUtil.joinListWithSeparator(professors, "/"), 
					oneClass.getName(), 
					oneClass.getFormattedAliasName(),
					otherClass.getName(),
					otherClass.getFormattedAliasName(),
					StringUtil.joinListWithSeparator(slots, "/")
				);
	}
	
	public List<SlotRange> getSlots(){
		return slots;
	}
	
	public List<Professor> getProfessors(){
		return professors;
	}
	
	public List<Class> getClasses(){
		List<Class> classes = new LinkedList<Class>();
		classes.add(oneClass); classes.add(otherClass);
		return classes;
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof SameProfessorsWarning)) return false;
		SameProfessorsWarning other = (SameProfessorsWarning) obj;
		
		return 	CollectionUtil.equalsWithoutOrder(getClasses(), other.getClasses())
				&& CollectionUtil.equalsWithoutOrder(professors, other.getProfessors())
				&& CollectionUtil.equalsWithoutOrder(slots, other.getSlots());
	}

	private Classroom getFirstRoomInConflictedSlots(){
		for(SlotRange slot : slots){
			if(slot.getClassroom() != null) return slot.getClassroom();
		}
		return null;
	}
	public InitialEditState getInfoToSolve(Class selected) {
		InitialEditState initialState = 
				new InitialEditState(selected, getFirstRoomInConflictedSlots(), professors.get(0));
		return initialState;
	}

	public List<NamedPair<Class>> getSolutionList() {
		List<NamedPair<Class>> solutions = new LinkedList<NamedPair<Class>>();
		solutions.add(new NamedPair<Class>("Editar " + oneClass.getName() + " ...", oneClass));
		solutions.add(new NamedPair<Class>("Editar " + otherClass.getName() + " ...", otherClass));
		return solutions;
	}

}
