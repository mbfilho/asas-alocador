package logic.services;

import data.persistentEntities.Professor;
import data.repository.Repository;
import data.repository.SimpleRepository;


public class ProfessorService extends BasicService<Professor> {

	private StateService stateService;
	
	public ProfessorService(){
		stateService = StateService.getInstance();
	}
	
	protected Repository<Professor> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().professors;
		else return new SimpleRepository<Professor>();
	}
	
	public boolean exists(String profName) {
		return list().exists(profName);
	}
}
