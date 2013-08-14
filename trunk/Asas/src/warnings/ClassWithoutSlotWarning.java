package warnings;

import java.util.LinkedList;
import java.util.List;

import classEditor.InitialEditState;
import classEditor.NamedPair;
import utilities.CollectionUtil;
import warnings.Warning;
import basic.Class;

public class ClassWithoutSlotWarning extends Warning {
	private static final long serialVersionUID = -5954713850002032549L;
	private Class theClass;
	
	public ClassWithoutSlotWarning(Class c){
		theClass = c;
	}
	
	public String getMessage() {
		return theClass.completeName();
	}
	
	public Class getTheClass(){
		return theClass;
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof ClassWithoutSlotWarning)) return false;
		ClassWithoutSlotWarning other = (ClassWithoutSlotWarning) obj;
		
		return theClass == other.getTheClass();
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
