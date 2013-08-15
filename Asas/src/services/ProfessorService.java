package services;

import java.util.Collection;

import repository.Repository;
import repository.SimpleRepository;
import basic.Professor;

public class ProfessorService {

	private StateService stateService;
	
	public ProfessorService(){
		stateService = StateService.getInstance();
	}
	
	private Repository<Professor> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().professors;
		else return new SimpleRepository<Professor>();
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
