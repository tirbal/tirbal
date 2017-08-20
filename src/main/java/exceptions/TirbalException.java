package main.java.exceptions;

/**
 * 
 * @author tbarthel
 *
 */
public class TirbalException extends Exception {
	//
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 */
	public TirbalException(final String message) {
		super(message);
	}
}
