package data.persistentEntities;

import java.io.Serializable;

import data.NamedEntity;


public class Classroom implements NamedEntity, Serializable{
	private static final long serialVersionUID = -3584658018972715164L;
	
	private String name;
	private int capacity;
	private boolean external;
	
	public Classroom(String name){
		this(name, -1);
	}
	
	public Classroom(String name, int cap){
		this.name = name;
		this.capacity = cap;
	}
	
	public Classroom(String name, String cap){
		try{
			capacity = Integer.parseInt(cap);
		}catch(NumberFormatException ex){
			capacity = -1;
		}
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

	public void setName(String name) {
		this.name = name;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
	
	public String toString(){
		return getName();
	}
}
