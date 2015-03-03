/**
 * 
 */
package com.ctm.test;

import org.junit.Test;

import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.utils.CTMCommonUtil;

/**
 * @author bgungor
 *
 */
public class CTMCommonUtilTest {

	@Test(expected=CTMRuntimeException.class)
	public void testGetFileReaderForNPEError() throws CTMRuntimeException {
		CTMCommonUtil.getFileReader(null);
	}
	
	@Test
	public void testGetFileReaderForSuccess() throws CTMRuntimeException {
		CTMCommonUtil.getFileReader("/input-test.txt");
	}

	@Test(expected=CTMRuntimeException.class)
	public void testGetFileReaderForWrongResourceError() throws CTMRuntimeException {
		CTMCommonUtil.getFileReader("/input-testttt.txt");
	}
}
