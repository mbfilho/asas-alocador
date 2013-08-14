package warnings;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import classEditor.InitialEditState;
import classEditor.NamedPair;

import utilities.CollectionUtil;
import utilities.StringUtil;

import basic.SlotRange;
import basic.Class;

public class ClassWithoutRoom extends Warning {

	private Class theClass;
	private Vector<SlotRange> slots;
	
	public ClassWithoutRoom(Class c, Vector<SlotRange> slots){
		theClass = c;
		this.slots = slots;
	}
	
	public Class getTheClass(){
		return theClass;
	}
	
	public Vector<SlotRange> getSlots(){
		return slots;
	}
	
	public String getMessage() {
		return String.format("<html>Turma <b>%s</b> está sem sala no(s) horário(s) <b>%s</b></html>", 
				theClass.completeName(),
				StringUtil.joinListWithSeparator(slots, "/")
			);
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof ClassWithoutRoom)) return false;
		ClassWithoutRoom other = (ClassWithoutRoom) obj;
		
		return theClass == other.getTheClass()
				&& CollectionUtil.equalsWithoutOrder(slots, other.getSlots());
	}
	
	public InitialEditState getInfoToSolve(Class selected) {
		InitialEditState initialState = 
				new InitialEditState(selected, null, CollectionUtil.firstOrDefault(selected.getProfessors()));
		return initialState;
	}

	public List<NamedPair<Class>> getSolutionList() {
		List<NamedPair<Class>> solutions = new LinkedList<NamedPair<Class>>();
		solutions.add(new NamedPair<Class>("Editar " + theClass.getName() + " ...", theClass));
		return solutions;
	}

}
