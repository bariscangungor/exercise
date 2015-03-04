/**
 * 
 */
package com.ctm.test;

import org.junit.Assert;
import org.junit.Test;

import com.ctm.model.impl.Talk;
import com.ctm.type.DurationType;

/**
 * @author bgungor
 *
 */
public class TalkTest {

	@Test
	public void testGetDurationForPrintOut() {
		Talk talk = new Talk("test1", DurationType.LIGTNING, 5);
		Assert.assertEquals("TalkType print out version should be correct!",
				DurationType.LIGTNING.value(), talk.getDurationForPrintOut());
	}

	@Test 
	public void testFindTalkType() {
		DurationType type = DurationType.findTalkType("test 70min");
		Assert.assertEquals(
				"The talk title ends with 'min' should be set as MINUTE! ",
				DurationType.MINUTES, type);

		type = DurationType.findTalkType("test lightning");
		Assert.assertEquals(
				"The talk title ends with 'lightning' should be set as LIGHTNING! ",
				DurationType.LIGTNING, type);
	}
}
