/**
 * 
 */
package com.ctm.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.logging.Level;

import com.ctm.exceptions.CTMRuntimeException;

/**
 * @author bgungor
 *
 */
public class CTMCommonUtil {

	public static BufferedReader getFileReader(String resource)	throws CTMRuntimeException {
		FileReader fileReader = null;
		try { 
			URL url = CTMCommonUtil.class.getClass().getResource(resource);

			if (url == null) {
				throw new CTMRuntimeException("Please check the file resource path!",Level.SEVERE.getName());
			}

			fileReader = new FileReader(url.getFile()); 
		} catch (FileNotFoundException e) {
			throw new CTMRuntimeException("File cannot be found. Please check the resource folder or file!", Level.SEVERE.getName(), e);
		} catch (Exception e) {
			throw new CTMRuntimeException("Unexpected exception occurred. Please find details in following message!", Level.SEVERE.getName(), e);
		}
		
		return new BufferedReader(fileReader);
	}
}
