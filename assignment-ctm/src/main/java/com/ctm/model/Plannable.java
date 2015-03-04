/**
 * 
 */
package com.ctm.model;

import com.ctm.type.SessionType;

/**
 * @author bgungor
 *
 */
public interface Plannable extends Comparable<Plannable> {

	int getDuration();
	
	boolean isPlanned();
	void setPlanned(boolean planned); 
	
	SessionType getSessionType();
	void setSessionType(SessionType sessionType);
}
