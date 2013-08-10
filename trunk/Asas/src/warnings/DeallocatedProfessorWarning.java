package warnings;

import basic.Professor;

public class DeallocatedProfessorWarning extends _Warning{

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
}
