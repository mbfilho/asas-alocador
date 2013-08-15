package services;


public class BasicService {

	protected int getCurrentId(){
		return StateService.getInstance().getCurrentState().getId();
	}
}
