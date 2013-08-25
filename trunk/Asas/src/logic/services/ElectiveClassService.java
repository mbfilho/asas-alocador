package logic.services;


import data.persistentEntities.ElectiveClass;
import data.repository.Repository;
import data.repository.SimpleRepository;

public class ElectiveClassService extends BasicService<ElectiveClass>{

	private StateService stateService;
	
	public ElectiveClassService(){
		stateService = StateService.getInstance();
	}
	
	protected Repository<ElectiveClass> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().elective;
		else return new SimpleRepository<ElectiveClass>();
	}
	
	public void add(ElectiveClass toAdd){
		toAdd.setId(getCurrentId());
		super.add(toAdd);
	}
}
