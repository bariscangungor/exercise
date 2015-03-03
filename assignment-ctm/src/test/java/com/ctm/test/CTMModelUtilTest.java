/**
 * 
 */
package com.ctm.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ctm.exceptions.CTMBusinessException;
import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.model.Talk;
import com.ctm.type.SessionType;
import com.ctm.type.TalkType;
import com.ctm.utils.CTMTalkUtil;

/**
 * @author bgungor
 *
 */
public class CTMModelUtilTest {
	List<Talk> initialTalkList = new ArrayList<Talk>();
	
	@Before
	public void init() {
		populateInitialTaskList();
	} 
 
	@Test(expected=CTMRuntimeException.class)
	public void testRecursiveTaskListPopulationForTargetMinError() throws CTMRuntimeException {
		int targetMin = -1; 
		
		List<Talk> cumulativeTalkList =  new ArrayList<Talk>();
		CTMTalkUtil.populateTalkListCumulativeAndRecursiveByParams(initialTalkList, cumulativeTalkList, null, targetMin);
		
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
		CTMTalkUtil.populateTalkListCumulativeAndRecursiveByParams(initialTalkList, cumulativeTalkList, null, targetMin);
		
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
		boolean result = CTMTalkUtil.populateTalkListCumulativeAndRecursiveByParams(initialTalkList, cumulativeTalkList, null, targetMin);
		
		Assert.assertEquals("error occurred in talk list population", true, result);
	} 

	@Test
	public void testRecursiveTaskListPopulationForOverallSuccess() throws CTMRuntimeException {
		int targetMin = 100; 
		
		List<Talk> cumulativeTalkList =  new ArrayList<Talk>();
		boolean result = CTMTalkUtil.populateTalkListCumulativeAndRecursiveByParams(initialTalkList, cumulativeTalkList, null, targetMin);
		
		Assert.assertEquals("error occurred in talk list population", true, result);
	} 
	
	@Test
	public void testGenerateTalkListFromResourceForSuccess() throws CTMRuntimeException {
		List<Talk> talkList = CTMTalkUtil.generateTalkListFromResource("/input-test.txt");
		Assert.assertNotEquals("The talkList is empty!", 0, talkList.size());
	}

	@Test(expected=CTMRuntimeException.class)
	public void testGenerateTalkListFromResourceForError() throws CTMRuntimeException {
		List<Talk> talkList = CTMTalkUtil.generateTalkListFromResource("/input-testttt.txt");
		Assert.assertEquals("The talkList must be empty but not!", 0, talkList.size());
	}
	
	@Test(expected=CTMRuntimeException.class)
	public void testGetTalkTimeFormatByDateForError() throws CTMRuntimeException {
		String date = CTMTalkUtil.getTalkTimeFormatByDate(null);
		Assert.assertTrue("Formatted date str must include at least one of the followings: PM or AM", date.contains("PM") || date.contains("AM"));
	}
	
	@Test
	public void testGetTalkTimeFormatByDateForSuccess() throws CTMRuntimeException {
		String date = CTMTalkUtil.getTalkTimeFormatByDate(new Date());
		Assert.assertTrue("Formatted date str must include at least one of the followings: PM or AM", date.contains("PM") || date.contains("AM"));
	}

	@Test(expected=CTMRuntimeException.class)
	public void testPlanSessionByParamsOverallError() throws CTMBusinessException, CTMRuntimeException {
		int targetMin = -1; 
		
		List<Talk> plannedList = CTMTalkUtil.planSessionByParams(initialTalkList, targetMin, SessionType.MORNING);
		
		Assert.assertNotEquals(0, plannedList.size());
	}
	
	@Test
	public void testPlanSessionByParamsForListSizeSuccess() throws CTMBusinessException, CTMRuntimeException {
		int targetMin = 100; 
		
		List<Talk> plannedList = CTMTalkUtil.planSessionByParams(initialTalkList, targetMin, SessionType.MORNING);
		
		Assert.assertNotEquals(0, plannedList.size());
	}
	
	private void populateInitialTaskList() {
		for(int i = 0 ; i < 100000 ; i++) {
			initialTalkList.add(new Talk(i+"", TalkType.MINUTES, (int) (Math.random() * 100)));
		}
	}
	
}
