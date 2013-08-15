package warnings.types;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import classEditor.InitialEditState;
import classEditor.NamedPair;

import utilities.CollectionUtil;
import utilities.StringUtil;

import basic.Class;
import basic.Classroom;
import basic.Professor;
import basic.SlotRange;

public class SameProfessorsWarning extends Warning{

	private static final long serialVersionUID = -151872803891658353L;
	
	private Class oneClass, otherClass;
	private Vector<Professor> professors;
	private Vector<SlotRange> slots;
	
	public SameProfessorsWarning(Class c1, Class c2, Vector<Professor> profs, Vector<SlotRange> ranges){
		oneClass = c1;
		otherClass = c2;
		professors = profs;
		slots = ranges;
	}
	
	public String getMessage() {
		return String.format("<html>Professor(es) <b>%s</b> nas turmas <b>%s</b> e <b>%s</b>, <b>%s</b></html>",
					StringUtil.joinListWithSeparator(professors, "/"), 
					oneClass.getName(), 
					otherClass.getName(),
					StringUtil.joinListWithSeparator(slots, "/")
				);
	}
	
	public Vector<SlotRange> getSlots(){
		return slots;
	}
	
	public Vector<Professor> getProfessors(){
		return professors;
	}
	
	public Vector<Class> getClasses(){
		Vector<Class> classes = new Vector<Class>();
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
				new InitialEditState(selected, getFirstRoomInConflictedSlots(), professors.firstElement());
		return initialState;
	}

	public List<NamedPair<Class>> getSolutionList() {
		List<NamedPair<Class>> solutions = new LinkedList<NamedPair<Class>>();
		solutions.add(new NamedPair<Class>("Editar " + oneClass.getName() + " ...", oneClass));
		solutions.add(new NamedPair<Class>("Editar " + otherClass.getName() + " ...", otherClass));
		return solutions;
	}

}
