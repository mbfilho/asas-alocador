package data.persistentEntities.warningsTypes;

import java.util.LinkedList;
import java.util.List;

import presentation.NamedPair;
import presentation.classes.InitialEditState;


import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.Warning;

public class DeallocatedProfessorWarning extends Warning{
	private static final long serialVersionUID = -7823634926094540844L;
	
	private Professor professor;
	
	public DeallocatedProfessorWarning(Professor p){
		professor = p;
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof DeallocatedProfessorWarning)) return false;
		DeallocatedProfessorWarning other = (DeallocatedProfessorWarning) obj;
		return professor == other.getProfessor();
	}

	public Professor getProfessor(){
		return professor;
	}
	
	public String getMessage() {
		return professor.getName();
	}

	public InitialEditState getInfoToSolve(Class selected) {
		return null;
	}

	public List<NamedPair<Class>> getSolutionList() {
		return new LinkedList<NamedPair <Class>>();
	}
}
