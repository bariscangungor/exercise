/**
 * 
 */
package com.ctm.exceptions;

import com.ctm.constants.CTMConstants;

/**
 * @author bgungor
 *
 */
public class CTMRuntimeException extends CTMGenericException { 
	private static final long serialVersionUID = 3031128825613691588L;

	private String errorCode = CTMConstants.EMPTY;

	public CTMRuntimeException(String message, String errorCode, Throwable t) {
		super(message, t);
		this.errorCode = errorCode;
	}
	
	public CTMRuntimeException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
