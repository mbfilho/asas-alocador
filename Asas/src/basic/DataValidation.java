package basic;

import java.util.Vector;

public class DataValidation <T>{
	public T data;
	public Vector<String> validation;
	
	public DataValidation(T data){
		this(data, new Vector<String>());
	}
	
	public DataValidation(T data, Vector<String> valid){
		this.data = data;
		this.validation = valid;
	}
	
	public DataValidation(){
		this.validation = new Vector<String>();
	}
	
	public String getErrors(){
		String errors = "";
		for(String error : validation) errors += error + "\n";
		return errors;
	}
}
