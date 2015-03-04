/**
 * 
 */
package com.ctm.exceptions;

import com.ctm.constants.CTMConstants;

/**
 * @author bgungor
 *
 */
public class CTMBusinessException extends CTMGenericException { 
	private static final long serialVersionUID = -316420705033826164L; 

	private String errorCode = CTMConstants.EMPTY;

	public CTMBusinessException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	} 
}
