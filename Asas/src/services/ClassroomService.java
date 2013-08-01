package services;

import java.util.Collection;

import javax.swing.text.SimpleAttributeSet;

import data.Repository;
import data.SimpleRepository;

import basic.Classroom;
import statePersistence.StateService;

public class ClassroomService {

	private StateService stateService;
	
	public ClassroomService(){
		stateService = StateService.getInstance();
	}
	
	private Repository<Classroom> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().classrooms;
		else return new SimpleRepository();
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
