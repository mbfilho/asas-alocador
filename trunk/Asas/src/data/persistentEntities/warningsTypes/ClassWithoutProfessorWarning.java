package data.persistentEntities.warningsTypes;

import java.util.LinkedList;
import java.util.List;

import presentation.NamedPair;
import presentation.classes.InitialEditState;

import utilities.CollectionUtil;

import data.persistentEntities.Class;
import data.persistentEntities.Warning;

public class ClassWithoutProfessorWarning extends Warning{
	private static final long serialVersionUID = -6207402524852673061L;
	
	private Class theClass;
	
	public ClassWithoutProfessorWarning(Class c){
		theClass = c;
	}
	
	public Class getTheClass(){
		return theClass;
	}
	
	public String getMessage() {
		return String.format("<html><b>%s<i>%s</i></b></html>", 
				theClass.completeName(),
				theClass.getFormattedAliasName());
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof ClassWithoutProfessorWarning)) return false;
		ClassWithoutProfessorWarning other = (ClassWithoutProfessorWarning) obj;
		return theClass == other.getTheClass();
	}

	public InitialEditState getInfoToSolve(Class selected) {
		InitialEditState initialState = 
				new InitialEditState(selected, CollectionUtil.firstOrDefault(selected.getAllRooms()), null);
		return initialState;
	}

	public List<NamedPair<Class>> getSolutionList() {
		List<NamedPair<Class>> solutions = new LinkedList<NamedPair<Class>>();
		solutions.add(new NamedPair<Class>("Editar " + theClass.getName() + " ...", theClass));
		return solutions;
	}
}
