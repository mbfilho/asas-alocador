package statePersistence;

import java.io.File;
import java.io.Serializable;

public class StateDescription implements Serializable{

	private String name;
	private String description;
	private boolean draft;
	
	public StateDescription(String nm, String desc, boolean draft){
		name = nm;
		description = desc;
		this.draft = draft;
	}
	public StateDescription(String nm, String desc){
		this(nm, desc, false);
	}
	
	public boolean isDraft(){
		return draft;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public File getFile(){
		return new File(name);
	}
}
