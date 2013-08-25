package data.persistentEntities;

import java.io.Serializable;
import java.util.List;

import presentation.NamedPair;
import presentation.classes.edition.InitialEditState;

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
	
	public abstract InitialEditState getInfoToSolve(Class selected);
	
	public abstract List<NamedPair<Class>> getSolutionList();
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
}
