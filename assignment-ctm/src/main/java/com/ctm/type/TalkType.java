/**
 * 
 */
package com.ctm.type;

/**
 * @author bgungor
 *
 */
public enum TalkType {
	LIGTNING("lightning"), MINUTES("min");
	
	private final String value;
	
	private TalkType(final String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
	
	public static TalkType findTalkType(String val) {
		return val.endsWith(LIGTNING.value) ? LIGTNING : 
				val.endsWith(MINUTES.value) ? MINUTES : null;
	}
}
