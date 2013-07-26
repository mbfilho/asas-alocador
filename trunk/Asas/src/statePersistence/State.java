package statePersistence;

import java.io.Serializable;

import basic.Class;
import basic.Classroom;
import basic.NamedEntity;
import basic.Professor;
import data.Repository;
import data.SimpleRepository;

public class State implements NamedEntity, Serializable{
	public StateDescription description;
	public Repository<Class> classes;
	public Repository<Classroom> classrooms;
	public Repository<Professor> professors;

	public State(){
		classes = new SimpleRepository<Class>();
		classrooms = new SimpleRepository<Classroom>();
		professors = new SimpleRepository<Professor>();
	}
	
	public String getName() {
		return description.getName();
	}

	public void setStateDescription(String name, String desc, boolean isDraft) {
		description = new StateDescription(name, desc, isDraft);
	}
}
