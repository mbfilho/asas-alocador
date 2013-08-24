package services;

import history.HistoryService;

import java.awt.Color;
import java.util.Collection;

import dataUpdateSystem.RegistrationCentral;

import repository.Repository;
import repository.SimpleRepository;

import utilities.ColorUtil;
import basic.Class;

public class ClassService extends BasicService{

	private StateService stateService;
	private final Color BASE_COLOR = Color.white;
	private double MIN_COLOR_DISTANCE = 0.2;
			
	public ClassService(){
		stateService = StateService.getInstance();
	}
	
	private Repository<Class> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().classes;
		else return new SimpleRepository<Class>();
	}
	
	public void add(Class c){
		c.setId(getCurrentId());
		c.setColor(generateColor());
		list().addInOrder(c);
	}
	
	public void update(Class c){
		list().update(c);
		HistoryService.getInstance().registerChange(String.format("Edição de '%s'", c.completeName()));
		RegistrationCentral.registerUpdate("Edição de turma");
	}
	
	public Collection<Class> all(){
		return list().all();
	}
	
	private Color generateColor(){
		Color color = null;
		
		for(int lim = 0; lim < 100; ++lim){
			color = ColorUtil.mixColors(BASE_COLOR, new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
			
			if(ColorUtil.getDistanceBetweenColors(color, Color.red) < MIN_COLOR_DISTANCE) continue;
			if(ColorUtil.getDistanceBetweenColors(color, Color.white) < MIN_COLOR_DISTANCE) continue;
			boolean dm = false;
			
			for(Class c : all()){
				if(ColorUtil.getDistanceBetweenColors(c.getColor(), color) < MIN_COLOR_DISTANCE) dm = true; 
			}
			
			if(!dm) return color;
			
		}
		return color;
	}

	public void remove(Class ec) {
		list().remove(ec);
		HistoryService.getInstance().registerChange(String.format("Remoção de '%s'", ec.completeName()));
		RegistrationCentral.registerUpdate("Remoção de turma");
	}

	public void completeSwap(Class theClass, Class other) {
		HistoryService.getInstance().registerChange(String.format("Swap entre '%s' e '%s'", theClass.completeName(), other.completeName()));
		RegistrationCentral.registerUpdate("Edição de turma");
	}
	
}
