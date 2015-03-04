/**
 * 
 */
package com.ctm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.ctm.exceptions.CTMBusinessException;
import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.model.Plannable;
import com.ctm.type.SessionType;

/**
 * @author bgungor
 *
 */
public class CTMPlanningUtil<T extends Plannable> {
	 
	/**
	 * 
	 * @param plannableList
	 * @param totalMin
	 * @param sessionType
	 * @return
	 * @throws CTMBusinessException
	 * @throws CTMRuntimeException
	 */
	public List<T> planSessionByParams(List<T> plannableList, int totalMin, SessionType sessionType) throws CTMBusinessException, CTMRuntimeException {
		List<T> session = new ArrayList<>();
		 
		if(totalMin < 1 || sessionType == null || plannableList == null || plannableList.size() == 0) {
			throw new CTMRuntimeException("Unable to plan the conference. Error occurred, please check the input params!", Level.SEVERE.getName());
		}
		
		boolean result = populatePlanningListByParams(plannableList, session, totalMin);
		
		if( !result ) {
			throw new CTMBusinessException("Unable to plan the conference regarding the given input data; namely the plannable list cannot be validated.", Level.WARNING.getName());
		}
		
		for(T planItem : session) {
			planItem.setSessionType(sessionType);
		}
		
		return session;
	}
 
	/**
	 * 
	 * @param plannableList
	 * @param cumulativeList
	 * @param remaining
	 * @return
	 * @throws CTMRuntimeException
	 */
	public boolean populatePlanningListByParams(List<T> plannableList, List<T> cumulativeList, int remaining) throws CTMRuntimeException {
		return populatePlannableListCumulativeAndRecursiveByParams(plannableList, cumulativeList, null, remaining);
	}   
	
	/**
	 * private methods begin
	 */
 
	/**
	 *  
	 * @return 	if the value is greater than 0, it represents the index of the value in given list
	 * 			if the value is -1, then it means the exact single planItem data cannot be found, but there is possibility still
	 * 			if the value is -2, then it means that it is impossible to find one or more planItem data to fit the remaining minutes
	 */
	private int discoverRemainingPlannable(int remainingMin, List<T> plannableList) {
		int possibility = -2;
		
		for(Plannable plannableItem : plannableList) {
			
			if( !plannableItem.isPlanned() ) {
				if( remainingMin == plannableItem.getDuration() ) {
					return plannableList.indexOf(plannableItem); // index
				} else if(plannableItem.getDuration() < remainingMin) {
					possibility = -1;
				}
			}
			
		}
		
		return possibility;
	}
	
	/**
	 * 
	 * @param plannableList
	 * @param cumulativeList
	 * @param plannableItem
	 * @param remaining
	 * @return
	 * @throws CTMRuntimeException
	 */
	private boolean populatePlannableListCumulativeAndRecursiveByParams(List<T> plannableList, List<T> cumulativeList, T plannableItem, int remaining) throws CTMRuntimeException {
		try { 
			if(remaining < 0) {
				plannableItem.setPlanned(false);
				cumulativeList.remove(plannableItem);
				return false;
			} else if(remaining == 0) {
				return true;
			}
			
			for(T currentPlanItem : plannableList) {
				if(!currentPlanItem.isPlanned()) {
					currentPlanItem.setPlanned(true);
					cumulativeList.add(currentPlanItem);
					
					if(populatePlannableListCumulativeAndRecursiveByParams(plannableList, cumulativeList, currentPlanItem, remaining - currentPlanItem.getDuration())) {
						return true;
					}
				}
			}
			 
			if( discoverRemainingPlannable(remaining, plannableList) < -1 ) { 
				plannableItem.setPlanned(false);
				cumulativeList.remove(plannableItem);
				return false;
			} 
		} catch (Exception e) {
			throw new CTMRuntimeException("Exception occurred during planning list population in recursive funtion!", Level.SEVERE.getName(), e);
		} 
		return true;
	}  
}
