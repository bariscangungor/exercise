/**
 * 
 */
package com.ctm.utils;

import static com.ctm.constants.CTMConstants.EMPTY;
import static com.ctm.constants.CTMConstants.LUNCH_PRINT_OUT;
import static com.ctm.constants.CTMConstants.NETWORKING_PRINT_OUT;
import static com.ctm.constants.CTMConstants.TALK_DATE_FORMAT;
import static com.ctm.constants.CTMConstants.TALK_DATE_PRINT_FORMAT;
import static com.ctm.constants.CTMConstants.TALK_START_AFTERNOON;
import static com.ctm.constants.CTMConstants.TALK_START_MORNING;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import com.ctm.constants.CTMConstants;
import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.model.impl.Talk;
import com.ctm.type.DurationType;

/**
 * @author bgungor
 *
 */
public final class CTMTalkUtil {
 
	/**
	 * 
	 * @param resource
	 * @return
	 * @throws CTMRuntimeException
	 */ 
	public static List<Talk> generateTalkListFromResource(String resource) throws CTMRuntimeException {
		List<Talk> talkList = new ArrayList<>();
		 
		try( BufferedReader br = CTMCommonUtil.getFileReader(resource) ) {
			for(String line; (line = br.readLine()) != null; ) {
	            talkList.add(generateTalkFromLine(line));
		    }
		} catch (FileNotFoundException e) {
			throw new CTMRuntimeException("Please check the file path!", Level.SEVERE.getName(), e);
		} catch (IOException e) { 
			throw new CTMRuntimeException("IO Exception occurred during file data initialization!", Level.SEVERE.getName(), e);
		} catch (Exception e) { 
			throw new CTMRuntimeException("Unexpected exception occurred during file data initialization!", Level.SEVERE.getName(), e);
		} 
		
		return talkList;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 * @throws CTMRuntimeException
	 */
	public static String getTalkTimeFormatByDate(Date date) throws CTMRuntimeException {
		try { 
			SimpleDateFormat sdf = new SimpleDateFormat(TALK_DATE_PRINT_FORMAT);
			return sdf.format(date); 
		} catch (NullPointerException e) {
			throw new CTMRuntimeException("Exception occurred during talk time format operation. Given date param is null"
							, Level.SEVERE.getName(), e);
		}
	}
	
	/**
	 * 
	 * @param talkList
	 * @throws CTMRuntimeException
	 */
	public static void printMorningTalks(List<Talk> talkList) throws CTMRuntimeException {
		Calendar cal = Calendar.getInstance();
		try {
			SimpleDateFormat format = new SimpleDateFormat(TALK_DATE_FORMAT); 
			Date date = format.parse(TALK_START_MORNING);
			
			cal.setTime(date);
		} catch (ParseException e) {
			throw new CTMRuntimeException("Please check the morning session start date params!", Level.SEVERE.getName(), e);
		}
		
		for(Talk talk : talkList) { 
			System.out.println(CTMTalkUtil.getTalkTimeFormatByDate(cal.getTime()) 
					+ " " + talk.getTitle() + " " + talk.getDurationForPrintOut());
			cal.add(Calendar.MINUTE, talk.getDuration());
		}
		System.out.println(LUNCH_PRINT_OUT); 
	}

	/**
	 * 
	 * @param talkList
	 * @throws CTMRuntimeException
	 */
	public static void printAfternoonTalks(List<Talk> talkList) throws CTMRuntimeException {
		Calendar cal = Calendar.getInstance();
		try {
			SimpleDateFormat format = new SimpleDateFormat(TALK_DATE_PRINT_FORMAT); 
			Date date = format.parse(TALK_START_AFTERNOON);
			
			cal.setTime(date);
		} catch (ParseException e) {
			throw new CTMRuntimeException("Please check the morning session start date params!", Level.SEVERE.getName(), e);
		}
		
		for(Talk talk : talkList) { 
			System.out.println(CTMTalkUtil.getTalkTimeFormatByDate(cal.getTime()) 
					+ " " + talk.getTitle() + " " + talk.getDurationForPrintOut());
			cal.add(Calendar.MINUTE, talk.getDuration());
		}
		System.out.println(CTMTalkUtil.getTalkTimeFormatByDate(cal.getTime()) + NETWORKING_PRINT_OUT);
	}

	/**
	 * private methods begin
	 */ 
	
	private static Talk generateTalkFromLine(String line) {
		return new Talk(filterTalkTitle(line), DurationType.findTalkType(line), findTalkDuration(line)); 
	}
	
	private static String filterTalkTitle(String title) {
		return title.replaceAll("[0-9]", EMPTY)
				.replace(DurationType.MINUTES.value(), EMPTY)
				.replace(DurationType.LIGTNING.value(), EMPTY).trim();
	} 
	
	private static int findTalkDuration(String line) {
		if( line.contains(DurationType.LIGTNING.value()) ) {
			return CTMConstants.LIGHTING_DURATION;
		}
		
		return Integer.valueOf(line.replaceAll("\\D+","")).intValue();
	}
}
