/**
 * 
 */
package com.ctm.test;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.model.impl.Talk;
import com.ctm.utils.CTMTalkUtil;

/**
 * @author bgungor
 *
 */
public class CTMTalkUtilTest { 
	
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
	
}
