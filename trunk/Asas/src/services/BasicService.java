package services;

import statePersistence.StateService;

public class BasicService {

	protected int getCurrentId(){
		return StateService.getInstance().getCurrentState().getId();
	}
}
