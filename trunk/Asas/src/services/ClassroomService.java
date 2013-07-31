package services;

import java.util.Collection;

import data.Repository;

import basic.Classroom;
import statePersistence.StateService;

public class ClassroomService {

	private StateService stateService;
	
	public ClassroomService(){
		stateService = StateService.getInstance();
	}
	
	private Repository<Classroom> list(){
		return stateService.getCurrentState().classrooms;
	}
	
	public void add(Classroom room){
		list().addInOrder(room);
	}
	
	public void update(Classroom room){
		list().update(room);
	}
	
	public Classroom getByName(String name){
		return list().get(name);
	}
	
	public Collection<Classroom> all(){
		return list().all();
	}
	
}
