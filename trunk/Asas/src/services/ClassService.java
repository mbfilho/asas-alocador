package services;

import java.awt.Color;
import java.util.Collection;

import data.Repository;
import data.SimpleRepository;
import statePersistence.StateService;
import basic.Class;
import basic.ElectiveClass;

public class ClassService extends BasicService{

	private StateService stateService;
	private final Color BASE_COLOR = Color.white;
	private double MIN_COLOR_DISTANCE = 0.3;
			
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
	}
	
	public Collection<Class> all(){
		return list().all();
	}
	
	private double getColorDistance(Color c1, Color c2){
		double dist = 0;
		int v1[] = {c1.getRed(), c1.getGreen(), c1.getBlue()};
		int v2[] = {c2.getRed(), c2.getGreen(), c2.getBlue()};
		
		for(int i = 0; i < 3; ++i)
			dist += (v2[i] - v1[i]) * (v2[i] - v1[i]);
		
		dist /= 255*Math.sqrt(3);
		return dist;	
	}
	
	private Color generateColor(){
		Color color = null;
		int base[] = {BASE_COLOR.getRed(), BASE_COLOR.getGreen(), BASE_COLOR.getBlue()};
		int rgb[] = new int[3]; 
		
		for(int lim = 0; lim < 100; ++lim){
			for(int i = 0; i < 3; ++i)
				rgb[i] = (int) (base[i] + Math.random() * 255) / 2;
			
			color = new Color(rgb[0], rgb[1], rgb[2]);
			
			if(getColorDistance(color, Color.red) < MIN_COLOR_DISTANCE) continue;
			if(getColorDistance(color, Color.white) < MIN_COLOR_DISTANCE) continue;
			
			for(Class c : all()){
				if(getColorDistance(c.getColor(), color) > MIN_COLOR_DISTANCE) 
					return color;
			}
			
		}
		return color;
	}

	public void remove(ElectiveClass ec) {
		list().remove(ec);
	}
	
}
