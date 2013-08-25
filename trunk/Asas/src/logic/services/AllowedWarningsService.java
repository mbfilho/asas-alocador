package logic.services;

import java.awt.Color;

import data.persistentEntities.Warning;
import data.repository.Repository;
import data.repository.SimpleRepository;



import utilities.ColorUtil;

public class AllowedWarningsService {
	private StateService stateService;
	
	public AllowedWarningsService(){
		stateService = StateService.getInstance();
	}
	
	public static Color getAllowedWarningColor(){
		return ColorUtil.mixWithWhite(Color.yellow);
	}
	
	private Repository<Warning> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().allowedWarnings;
		else return new SimpleRepository<Warning>();
	}
	
	public void allow(Warning w){
		if(!isAllowed(w))
			list().addInOrder(w);
	}
	
	public void disallow(Warning w){
		list().remove(w);
	}
	
	public boolean isAllowed(Warning w){
		return list().all().contains(w);
	}
}
