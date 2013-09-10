package logic.services;


import java.awt.Color;
import java.util.List;

import logic.dataUpdateSystem.DataUpdateCentral;
import logic.historySystem.HistoryService;

import data.persistentEntities.Class;
import data.persistentEntities.Professor;
import data.persistentEntities.SlotRange;
import data.repository.Repository;
import data.repository.SimpleRepository;


import utilities.ColorUtil;

public class ClassService extends BasicDataAccessService<Class>{

	private StateService stateService;
	private final Color BASE_COLOR = Color.white;
	private double MIN_COLOR_DISTANCE = 0.2;
			
	public ClassService(){
		stateService = StateService.getInstance();
	}
	
	protected Repository<Class> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().classes;
		else return new SimpleRepository<Class>();
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
	
	public void remove(Class ec) {
		super.remove(ec);
		HistoryService.getInstance().registerChange(String.format("Remoção de '%s'", ec.completeName()));
		DataUpdateCentral.registerUpdate("Remoção de turma");
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
