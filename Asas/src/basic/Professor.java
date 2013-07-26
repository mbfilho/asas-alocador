package basic;

import java.io.Serializable;


public class Professor implements NamedEntity, Serializable{
	private static final long serialVersionUID = 9092577287084935806L;
	
	private String name;
	private String cargo;
	private String dpto;
	private String email;
	private boolean temporary;
	private boolean away;
	
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
}
