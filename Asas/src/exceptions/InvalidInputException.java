package exceptions;

import java.util.Vector;

public class InvalidInputException extends Exception{
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
