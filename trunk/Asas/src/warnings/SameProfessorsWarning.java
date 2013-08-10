package warnings;

import java.util.Vector;

import utilities.CollectionUtil;
import utilities.StringUtil;

import basic.Class;
import basic.Professor;
import basic.SlotRange;

public class SameProfessorsWarning extends Warning{

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

}
