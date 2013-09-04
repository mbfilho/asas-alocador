package exceptions;

import java.io.IOException;

public class WritingException extends Exception {

	private static final long serialVersionUID = 6443137342782524894L;
	
	public WritingException(IOException ioe){
		super(ioe);
	}

}
