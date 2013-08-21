package exceptions;

public class StateIOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9170584324955454084L;

	public StateIOException(String string, Exception ex) {
		super(string, ex);
	}

}
