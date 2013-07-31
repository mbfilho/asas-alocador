package services;

import java.util.Collection;

import data.Repository;
import basic.Professor;
import statePersistence.StateService;

public class ProfessorService {

	private StateService stateService;
	
	public ProfessorService(){
		stateService = StateService.getInstance();
	}
	
	private Repository<Professor> list(){
		return stateService.getCurrentState().professors;
	}
	
	public void update(Professor prof){
		list().update(prof);
	}
	
	public void add(Professor prof){
		list().addInOrder(prof);
	}
	
	public Professor getByName(String name){
		return list().get(name);
	}
	
	public Collection<Professor> all(){
		return list().all();
	}

	public boolean exists(String profName) {
		return list().exists(profName);
	}
}
