package warnings;

import java.awt.Color;

import data.Repository;
import data.SimpleRepository;

import statePersistence.StateService;
import utilities.ColorUtil;

public class AllowedWarningsService {
	private StateService stateService;
	
	public AllowedWarningsService(){
		stateService = StateService.getInstance();
	}
	
	public static Color getAllowedWarningColor(){
		return ColorUtil.mixColors(Color.yellow, Color.white);
	}
	
	private Repository<_Warning> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().allowedWarnings;
		else return new SimpleRepository<_Warning>();
	}
	
	public void allow(_Warning w){
		if(!isAllowed(w))
			list().addInOrder(w);
	}
	
	public void disallow(_Warning w){
		list().remove(w);
	}
	
	public boolean isAllowed(_Warning w){
		return list().all().contains(w);
	}
}
