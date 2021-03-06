package data.persistentEntities;

import java.io.Serializable;
import java.util.List;

import presentation.NamedPair;
import presentation.classes.InitialEditState;

import data.NamedEntity;

public abstract class Warning implements Serializable, NamedEntity{
	
	private static final long serialVersionUID = -6932389535095046716L;

	public abstract String getMessage();
	public abstract boolean equals(Object obj);
	
	public String toString(){
		return getMessage();
	}
	
	public String getName(){
		return getMessage();
	}
	
	/*TODO:Substituir os metodos abstratos por esses
	public abstract List<Class> getConflictedClasses();
	public abstract List<Professor> getConflictedProfessors();
	public abstract Classroom getConflictedRoom();
	//*/
	
	public abstract InitialEditState getInfoToSolve(Class selected);
	
	public abstract List<NamedPair<Class>> getSolutionList();
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
}
