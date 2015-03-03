/**
 * 
 */
package com.ctm.test;

import org.junit.Assert;
import org.junit.Test;

import com.ctm.model.Talk;
import com.ctm.type.TalkType;

/**
 * @author bgungor
 *
 */
public class TalkTest {

	@Test
	public void testGetDurationForPrintOut() {
		Talk talk = new Talk("test1", TalkType.LIGTNING, 5);
		Assert.assertEquals("TalkType print out version should be correct!",
				TalkType.LIGTNING.value(), talk.getDurationForPrintOut());
	}

	@Test
	public void testFindTalkType() {
		TalkType type = TalkType.findTalkType("test 70min");
		Assert.assertEquals(
				"The talk title ends with 'min' should be set as MINUTE! ",
				TalkType.MINUTES, type);

		type = TalkType.findTalkType("test lightning");
		Assert.assertEquals(
				"The talk title ends with 'lightning' should be set as LIGHTNING! ",
				TalkType.LIGTNING, type);
	}
}
