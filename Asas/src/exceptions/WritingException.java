package exceptions;

public class WritingException extends Exception {

	private static final long serialVersionUID = 6443137342782524894L;
	
	public WritingException(Exception ioe){
		super(ioe);
	}
	
	public WritingException(Exception e, String message){
		super(message, e);
	}

}
