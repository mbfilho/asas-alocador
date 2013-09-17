package logic.services;


import java.util.LinkedList;
import java.util.List;

import logic.dataUpdateSystem.DataUpdateCentral;
import data.persistentEntities.Classroom;
import data.persistentEntities.State;
import data.repository.Repository;

public class ClassroomService extends BasicDataAccessService<Classroom>{
	
	public static ClassroomService createServiceFromCurrentState(){
		return new ClassroomService(StateService.getInstance().getCurrentState());
	}
	
	public ClassroomService(State dataState){
		super(dataState);
	}
	
	protected Repository<Classroom> list(){
		return getState().classrooms;
	}
	
	public List<Classroom> allNonExternals(){
		List<Classroom> filtered = new LinkedList<Classroom>();
		
		for(Classroom room : all()){
			if(!room.isExternal())
				filtered.add(room);
		}
		
		return filtered;
	}
	
	public void update(Classroom room){
		super.update(room);
		DataUpdateCentral.registerUpdate("Informações de sala editadas");
	}
}
