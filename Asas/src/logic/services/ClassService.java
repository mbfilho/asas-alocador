package logic.services;

import java.awt.Color;
import java.util.List;

import logic.dataUpdateSystem.DataUpdateCentral;
import logic.historySystem.HistoryService;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;
import data.persistentEntities.State;
import data.repository.Repository;


import utilities.ColorUtil;

public class ClassService extends BasicDataAccessService<Class>{

	private final Color BASE_COLOR = Color.white;
	private double MIN_COLOR_DISTANCE = 0.2;
			
	public static ClassService createServiceFromCurrentState(){
		return new ClassService(StateService.getInstance().getCurrentState());
	}
	
	public ClassService(State dataState){
		super(dataState);
	}
	
	protected Repository<Class> list(){
		return getState().classes;
	}
	
	public void add(Class c){
		c.setId(getCurrentId());
		c.setColor(generateColor());
		super.add(c);
	}
	
	public void update(Class c){
		super.update(c);
		HistoryService.getInstance().registerChange(String.format("Edição de '%s'", c.completeName()));
		DataUpdateCentral.registerUpdate("Edição de turma");
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

	public Class getById(int id){
		for(Class c : all()){
			if(c.getId() == id) return c;
		}
		return null;
	}
	
	public void completeSwap(int oneId, List<Professor> oneClassProfs,
			List<SlotRange> oneClassSlots, Class another,
			List<Professor> otherClassProfs, List<SlotRange> otherClassSlots) {
		Class one = getById(oneId);
		one.setProfessors(oneClassProfs);
		one.setSlots(oneClassSlots);
		another.setProfessors(otherClassProfs);
		another.setSlots(otherClassSlots);
		HistoryService.getInstance().registerChange(String.format("Swap entre '%s' e '%s'", one.completeName(), another.completeName()));
		DataUpdateCentral.registerUpdate("Edição de turma");
	}
	
}
