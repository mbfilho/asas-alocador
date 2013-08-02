package services;

import java.io.Serializable;
import java.util.Collection;

import basic.ElectiveClassPreferences;
import data.Repository;
import data.SimpleRepository;
import statePersistence.StateService;

public class ElectivePreferencesService{

	private StateService stateService;
	
	public ElectivePreferencesService() {
		stateService = StateService.getInstance();
	}
	
	private Repository<ElectiveClassPreferences> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().electivePreferences;
		else return new SimpleRepository<ElectiveClassPreferences>();
	}
	
	public Collection<ElectiveClassPreferences> all(){
		return list().all();
	}
	
	public void add(ElectiveClassPreferences pref){
		list().addInOrder(pref);
	}
	
}
