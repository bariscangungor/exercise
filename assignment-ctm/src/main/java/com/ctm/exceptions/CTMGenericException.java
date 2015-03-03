/**
 * 
 */
package com.ctm.exceptions;

/**
 * @author bgungor
 *
 */
public class CTMGenericException extends Exception { 
	private static final long serialVersionUID = -6293695529461940845L;

	/**
	 * 
	 * @param message
	 * @param t
	 */
	public CTMGenericException(String message, Throwable t) {
		super(message, t);
	}

	/**
	 * @param message
	 */
	public CTMGenericException(String message) {
		super(message);
	} 
}
