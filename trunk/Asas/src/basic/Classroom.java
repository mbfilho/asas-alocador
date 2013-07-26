package basic;

import java.io.Serializable;

public class Classroom implements NamedEntity, Serializable{
	private String name;
	private int capacity;
	
	public Classroom(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
