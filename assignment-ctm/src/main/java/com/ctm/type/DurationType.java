/**
 * 
 */
package com.ctm.type;

/**
 * @author bgungor
 *
 */
public enum DurationType {
	LIGTNING("lightning"), MINUTES("min");
	
	private final String value;
	
	private DurationType(final String value) {
		this.value = value;
	}
	
	public String value() { 
		return this.value;
	}
	
	public static DurationType findTalkType(String val) {
		return val.endsWith(LIGTNING.value) ? LIGTNING : 
				val.endsWith(MINUTES.value) ? MINUTES : null;
	}
}
