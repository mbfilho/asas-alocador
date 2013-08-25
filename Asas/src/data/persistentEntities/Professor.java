package data.persistentEntities;

import java.io.Serializable;

import data.NamedEntity;



public class Professor implements NamedEntity, Serializable{
	private static final long serialVersionUID = 9092577287084935806L;
	
	private String name;
	private String cargo;
	private String dpto;
	private String email;
	private boolean temporary;
	private boolean away;
	
	
	public Professor(){}
	
	public Professor(String name, String email, String cargo, String dpto, boolean tmp, boolean away){
		this.name = name;
		this.email = email;
		this.cargo = cargo;
		this.dpto = dpto;
		this.temporary = tmp;
		this.away = away;
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public void setDpto(String dpto) {
		this.dpto = dpto;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	public void setAway(boolean away) {
		this.away = away;
	}

	public String getCargo() {
		return cargo;
	}

	public String getDpto() {
		return dpto;
	}

	public String getEmail(){
		return email;
	}
	
	public boolean isTemporary() {
		return temporary;
	}

	public boolean isAway() {
		return away;
	}
		
	public String toString(){
		return getName();
	}
}
