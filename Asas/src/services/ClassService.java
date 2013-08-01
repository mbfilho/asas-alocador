package services;

import java.util.Collection;

import data.Repository;
import data.SimpleRepository;
import statePersistence.StateService;
import basic.Class;

public class ClassService {

	private StateService stateService;

	public ClassService(){
		stateService = StateService.getInstance();
	}
	
	private Repository<Class> list(){
		if(stateService.hasValidState()) return stateService.getCurrentState().classes;
		else return new SimpleRepository();
	}
	
	public int cont(){
		return all().size();
	}
	
	public void add(Class c){
		c.setId(cont());
		c.setColor(generateColor());
		//System.out.println(c.getName() + " " + c.getId() +  " " + c.getHtmlColor());
		list().addInOrder(c);
	}
	
	public void update(Class c){
		list().update(c);
	}
	
	public Collection<Class> all(){
		return list().all();
	}
	
	private double getDist(String c1, String c2){
		double dist = 0;
		for(int i = 0; i < 3; ++i){
			int v1 = Integer.parseInt(c1.substring(2*i, 2*i+1),16);
			int v2 = Integer.parseInt(c2.substring(2*i, 2*i+1),16);
			dist += (v2 - v1) * (v2 - v1);
		}
		dist /= 255*Math.sqrt(3);
		return dist;	
	}
	
	private String generateColor(){
		String color = "";
		for(int lim = 0; lim < 100; ++lim){
			color = "";
			for(int i = 0; i < 3; ++i)
				color += String.format("%02x", (int) (Math.random() * 255));
			
			if(getDist(color, "ff0000") < 0.2) continue;
			
			for(Class c : all()){
				if(getDist(c.getHtmlColor().replace("#", ""), color) > 0.2) 
					return color;
			}
			
		}
		return color;
	}
	
}
