package data.configurations;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class StateDescription implements Serializable{
	private static final long serialVersionUID = 1380469572798348853L;
	private String name;
	private String description;
	
	public static StateDescription withTimeStamp(String name){
		String stamp = new Date().toString().replace(":", "-").replace(" ", "_");
		return new StateDescription(name + "-" + stamp, name + "-" + stamp);
	}
	
	public StateDescription(String nm, String desc){
		name = nm;
		description = desc;
	}

	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public File getFile(){
		return new File(String.format("savedStates%s%s", File.separator, name));
	}
}
