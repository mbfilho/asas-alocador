package logic.services;


import logic.dataUpdateSystem.DataUpdateCentral;
import data.persistentEntities.Classroom;
import data.repository.Repository;
import data.repository.SimpleRepository;

public class ClassroomService extends BasicDataAccessService<Classroom>{

	private StateService stateService;
	
	public ClassroomService(){
		stateService = StateService.getInstance();
	}
	
	protected Repository<Classroom> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().classrooms;
		else return new SimpleRepository<Classroom>();
	}
	
	public void update(Classroom room){
		super.update(room);
		DataUpdateCentral.registerUpdate("Informações de sala editadas");
	}
}
