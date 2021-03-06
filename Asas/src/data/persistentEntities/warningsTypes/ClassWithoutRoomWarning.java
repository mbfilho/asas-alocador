package data.persistentEntities.warningsTypes;

import java.util.LinkedList;
import java.util.List;

import presentation.NamedPair;
import presentation.classes.InitialEditState;

import data.persistentEntities.Class;
import data.persistentEntities.SlotRange;
import data.persistentEntities.Warning;

import utilities.CollectionUtil;
import utilities.StringUtil;


public class ClassWithoutRoomWarning extends Warning {
	private static final long serialVersionUID = -8507590734333203566L;
	
	private Class theClass;
	private List<SlotRange> slots;
	
	public ClassWithoutRoomWarning(Class c, List<SlotRange> slots){
		theClass = c;
		this.slots = slots;
	}
	
	public Class getTheClass(){
		return theClass;
	}
	
	public List<SlotRange> getSlots(){
		return slots;
	}
	
	public String getMessage() {
		return String.format("<html>Turma <b>%s<i>%s</i></b> está sem sala no(s) horário(s) <b>%s</b></html>", 
				theClass.completeName(),
				theClass.getFormattedAliasName(),
				StringUtil.joinListWithSeparator(slots, "/")
			);
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof ClassWithoutRoomWarning)) return false;
		ClassWithoutRoomWarning other = (ClassWithoutRoomWarning) obj;
		
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
