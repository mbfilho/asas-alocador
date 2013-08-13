package warnings;

import java.util.LinkedList;
import java.util.List;

import classEditor.InitialEditState;
import classEditor.NamedPair;
import basic.Class;
import basic.Professor;

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
