package basic;

import java.io.Serializable;

public class Classroom implements NamedEntity, Serializable{
	private static final long serialVersionUID = -3584658018972715164L;
	
	private String name;
	private int capacity;
	
	public Classroom(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setCapacity(int c){
		this.capacity = c;
	}
	
	public int getCapacity(){
		return capacity;
	}
}
