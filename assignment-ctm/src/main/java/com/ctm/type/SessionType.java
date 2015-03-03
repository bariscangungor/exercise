/**
 * 
 */
package com.ctm.type;

/**
 * @author bgungor
 *
 */
public enum SessionType {
	MORNING(1), AFTERNOON(2);
	
	private final int value;
	
	private SessionType(final int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	} 
}
