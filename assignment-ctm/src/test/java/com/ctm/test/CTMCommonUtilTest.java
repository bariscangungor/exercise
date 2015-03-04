/**
 * 
 */
package com.ctm.test;

import java.io.BufferedReader; 

import org.junit.Assert;
import org.junit.Test;

import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.utils.CTMCommonUtil;

/**
 * @author bgungor
 *
 */
public class CTMCommonUtilTest {
	
	@Test
	public void testGetFileReaderForSuccess() throws CTMRuntimeException {
		BufferedReader fileReader = CTMCommonUtil.getFileReader("/input-test.txt");
		Assert.assertNotNull(fileReader);
	}

	@Test(expected=CTMRuntimeException.class)
	public void testGetFileReaderForWrongResourceError() throws CTMRuntimeException {
		BufferedReader fileReader = CTMCommonUtil.getFileReader("/input-testttt.txt");
		Assert.assertNull(fileReader);
	}
	 
	@Test(expected=CTMRuntimeException.class)
	public void testGetFileReaderForNPEError() throws CTMRuntimeException {
		BufferedReader fileReader = CTMCommonUtil.getFileReader(null);
		Assert.assertNull(fileReader);
	}
}
