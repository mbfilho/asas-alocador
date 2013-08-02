package services;

import java.util.Collection;

import basic.ElectiveClass;
import data.Repository;
import data.SimpleRepository;
import statePersistence.StateService;

public class ElectiveClassService extends BasicService{

	private StateService stateService;
	
	public ElectiveClassService(){
		stateService = StateService.getInstance();
	}
	
	private Repository<ElectiveClass> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().elective;
		else return new SimpleRepository<ElectiveClass>();
	}
	
	public Collection<ElectiveClass> all(){
		return list().all();
	}
	
	public void add(ElectiveClass toAdd){
		toAdd.setId(getCurrentId());
		list().addInOrder(toAdd);
	}
	
	public ElectiveClass getByName(String name){
		for(ElectiveClass c : all()) if(c.getName().equals(name)) return c;
		return null;
	}
}
