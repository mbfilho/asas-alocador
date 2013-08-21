package exceptions;

import java.util.Vector;

public class InvalidInputException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2422313422346786315L;
	private Vector<String> messages;
	
	private InvalidInputException(){
		this.messages = new Vector<String>();
	}
	
	public InvalidInputException(String msg){
		this();
		this.messages.add(msg);
	}

	public InvalidInputException(Vector<String> errors) {
		messages = errors;
	}
	
	public String getMessage(){
		String msg = "";
		for(String m : messages)
			msg += m + "\n";
		return msg;
	}
}
