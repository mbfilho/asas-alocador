package statePersistence;

import java.io.Serializable;

import services.ElectivePreferencesService;
import warnings._Warning;

import allocation.AllocationResult;
import basic.Class;
import basic.Classroom;
import basic.ElectiveClass;
import basic.ElectiveClassPreferences;
import basic.NamedEntity;
import basic.Professor;
import data.Repository;
import data.SimpleRepository;

public class State implements NamedEntity, Serializable{
	public StateDescription description;
	public Repository<Class> classes;
	public Repository<Classroom> classrooms;
	public Repository<Professor> professors;
	public Repository<ElectiveClass> elective;
	public Repository<ElectiveClassPreferences> electivePreferences;
	public Repository<_Warning> allowedWarnings;
	
	private int _currentId;
	
	public State(){
		classes = new SimpleRepository<Class>();
		classrooms = new SimpleRepository<Classroom>();
		professors = new SimpleRepository<Professor>();
		elective = new SimpleRepository<ElectiveClass>();
		electivePreferences =  new SimpleRepository<ElectiveClassPreferences>();
		allowedWarnings = new SimpleRepository<_Warning>();
		_currentId = 1;
	}
	
	public String getName() {
		return description.getName();
	}

	public void setStateDescription(String name, String desc, boolean isDraft) {
		description = new StateDescription(name, desc, isDraft);
	}
	
	public int getId(){
		return _currentId++;
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
}
