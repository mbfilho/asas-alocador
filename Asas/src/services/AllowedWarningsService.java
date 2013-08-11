package services;

import java.awt.Color;

import data.Repository;
import data.SimpleRepository;

import statePersistence.StateService;
import utilities.ColorUtil;
import warnings.Warning;

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
