/**
 * 
 */
package com.ctm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ctm.exceptions.CTMBusinessException;
import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.model.impl.Talk;
import com.ctm.type.DurationType;
import com.ctm.type.SessionType;
import com.ctm.utils.CTMPlanningUtil;

/**
 * @author bgungor
 *
 */
public class CTMPlanningUtilTest {
	private List<Talk> initialPlannableList = new ArrayList<>();
	
	@Before
	public void init() {
		populateInitialPlannableList();
	} 
 
	@Test(expected=CTMRuntimeException.class)
	public void testRecursiveTaskListPopulationForTargetMinError() throws CTMRuntimeException {
		int targetMin = -1; 
		 
		List<Talk> cumulativeTalkList =  new ArrayList<Talk>();
		CTMPlanningUtil<Talk> ctmPlanningUtil = new CTMPlanningUtil<>();
		
		ctmPlanningUtil.populatePlanningListByParams(initialPlannableList, cumulativeTalkList, targetMin);
		 
		int count = 0;
		for(Talk talk : cumulativeTalkList) {
			count += talk.getDuration(); 
		}
		
		Assert.assertNotEquals("target minutes value does not match with the calculated value", targetMin, count);
	} 

	@Test
	public void testRecursiveTaskListPopulationForTargetMinSuccess() throws CTMRuntimeException {
		int targetMin = 100; 
		
		List<Talk> cumulativeTalkList =  new ArrayList<Talk>();
		CTMPlanningUtil<Talk> ctmPlanningUtil = new CTMPlanningUtil<>();

		ctmPlanningUtil.populatePlanningListByParams(initialPlannableList, cumulativeTalkList, targetMin);
		
		int count = 0;
		for(Talk talk : cumulativeTalkList) {
			count += talk.getDuration(); 
		}
		
		Assert.assertEquals("target minutes value does not match with the calculated value", targetMin, count);
	} 

	@Test
	public void testRecursiveTaskListPopulationForErrorResult() throws CTMRuntimeException {
		int targetMin = 100; 
		
		List<Talk> cumulativeTalkList =  new ArrayList<Talk>();
		CTMPlanningUtil<Talk> ctmPlanningUtil = new CTMPlanningUtil<>();

		boolean result = ctmPlanningUtil.populatePlanningListByParams(initialPlannableList, cumulativeTalkList, targetMin);
		
		Assert.assertEquals("error occurred in talk list population", true, result);
	} 

	@Test
	public void testRecursiveTaskListPopulationForOverallSuccess() throws CTMRuntimeException {
		int targetMin = 100; 
		
		List<Talk> cumulativeTalkList =  new ArrayList<Talk>();
		CTMPlanningUtil<Talk> ctmPlanningUtil = new CTMPlanningUtil<>();

		boolean result = ctmPlanningUtil.populatePlanningListByParams(initialPlannableList, cumulativeTalkList, targetMin);
		
		Assert.assertEquals("error occurred in talk list population", true, result);
	}  

	@Test(expected=CTMRuntimeException.class)
	public void testPlanSessionByParamsOverallError() throws CTMBusinessException, CTMRuntimeException {
		int targetMin = -1; 

		CTMPlanningUtil<Talk> ctmPlanningUtil = new CTMPlanningUtil<>(); 
		List<Talk> plannedList = ctmPlanningUtil.planSessionByParams(initialPlannableList, targetMin, SessionType.MORNING);
		
		Assert.assertNotEquals(0, plannedList.size());
	}
	
	@Test
	public void testPlanSessionByParamsForListSizeSuccess() throws CTMBusinessException, CTMRuntimeException {
		int targetMin = 100; 

		CTMPlanningUtil<Talk> ctmPlanningUtil = new CTMPlanningUtil<>();
		List<Talk> plannedList = ctmPlanningUtil.planSessionByParams(initialPlannableList, targetMin, SessionType.MORNING);
		
		Assert.assertNotEquals(0, plannedList.size());
	}
	
	private void populateInitialPlannableList() {
		for(int i = 0 ; i < 100000 ; i++) {
			initialPlannableList.add(new Talk(i+"", DurationType.MINUTES, (int) (Math.random() * 100)));
		}
	}
	
}
