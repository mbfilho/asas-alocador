package data;

import java.io.Serializable;
import java.util.Vector;

import basic.Class;

public class ClassRepository extends SimpleRepository<Class> implements Serializable{

	public int cont = 0;
	
	public ClassRepository() {
		super();
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
	
	public void addInOrder(Class c){
		c.setId(cont++);
		c.setColor(generateColor());
		System.out.println(c.getName() + " " + c.getId() +  " " + c.getHtmlColor());
		super.addInOrder(c);
	}

}
