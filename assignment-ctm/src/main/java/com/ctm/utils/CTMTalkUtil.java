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
import com.ctm.exceptions.CTMBusinessException;
import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.model.Talk;
import com.ctm.type.SessionType;
import com.ctm.type.TalkType;

/**
 * @author bgungor
 *
 */
public class CTMTalkUtil {
	
	/**
	 * 
	 * @param initialTalkList
	 * @param totalMin
	 * @param sessionType
	 * @return
	 * @throws CTMBusinessException 
	 * @throws CTMRuntimeException 
	 */
	public static List<Talk> planSessionByParams(List<Talk> initialTalkList, int totalMin, SessionType sessionType) throws CTMBusinessException, CTMRuntimeException {
		List<Talk> session = new ArrayList<>();
		
		if(totalMin < 1 || sessionType == null || initialTalkList == null || initialTalkList.size() == 0) {
			throw new CTMRuntimeException("Unable to plan the conference. Error occurred, please check the input params!", Level.SEVERE.getName());
		}
		
		boolean result = populateTalkListCumulativeAndRecursiveByParams(initialTalkList, session, null, totalMin);
		
		if( !result ) {
			throw new CTMBusinessException("Unable to plan the conference regarding the given input data; namely the talk list cannot be validated.", Level.WARNING.getName());
		}
		
		for(Talk talk : session) {
			talk.setSessionType(sessionType);
		}
		
		return session;
	} 
	
	/**
	 * 
	 * @param talkList
	 * @param cumulativeTalkList
	 * @param talk
	 * @param remaining
	 * @return
	 * @throws CTMRuntimeException 
	 */
	public static boolean populateTalkListCumulativeAndRecursiveByParams(List<Talk> talkList, List<Talk> cumulativeTalkList, Talk talk, int remaining) throws CTMRuntimeException {
		try { 
			if(remaining < 0) {
				talk.setPlanned(false);
				cumulativeTalkList.remove(talk);
				return false;
			}
			if(remaining == 0) {
				return true;
			}
			
			for(Talk currentTalk : talkList) {
				if(!currentTalk.isPlanned()) {
					currentTalk.setPlanned(true);
					cumulativeTalkList.add(currentTalk);
					
					if(populateTalkListCumulativeAndRecursiveByParams(talkList, cumulativeTalkList, currentTalk, remaining - currentTalk.getDuration())) {
						return true;
					}
				}
			}
			
			int index = discoverRemainingTalk(remaining, talkList);
			if(index < -1) { 
				talk.setPlanned(false);
				cumulativeTalkList.remove(talk);
				return false;
			}
		
		} catch (Exception e) {
			throw new CTMRuntimeException("Exception occurred during talk list population in recursive funtion!", Level.SEVERE.getName(), e);
		}
		
		return true;
	}
	  
	public static List<Talk> generateTalkListFromResource(String resource) throws CTMRuntimeException {
		List<Talk> talkList = new ArrayList<>();
		
		try(BufferedReader br = CTMCommonUtil.getFileReader(resource)) {
			for(String line; (line = br.readLine()) != null;) {
	            talkList.add(generateTalkFromLine(line));
		    }
		} catch (FileNotFoundException e) {
			throw new CTMRuntimeException("Please check the file path!", Level.SEVERE.getName(), e);
		} catch (IOException e) { 
			throw new CTMRuntimeException("IO Exception occurred during file data initialization!", Level.SEVERE.getName(), e);
		} 
		
		return talkList;
	}
	
	public static String getTalkTimeFormatByDate(Date date) throws CTMRuntimeException {
		try { 
			SimpleDateFormat sdf = new SimpleDateFormat(TALK_DATE_PRINT_FORMAT);
			return sdf.format(date); 
		} catch (NullPointerException e) {
			throw new CTMRuntimeException("Exception occurred during talk time format operation. Given date param is null"
							, Level.SEVERE.getName(), e);
		}
	}

	public static void printMorningSession(List<Talk> talkList) throws CTMRuntimeException {
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
		System.err.println(LUNCH_PRINT_OUT);
	}

	public static void printAfternoonSession(List<Talk> talkList) throws CTMRuntimeException {
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
		System.err.println(CTMTalkUtil.getTalkTimeFormatByDate(cal.getTime()) + NETWORKING_PRINT_OUT);
	}

	/**
	 * private methods begin
	 */
 
	/**
	 * 
	 * @param remainingMin
	 * @param initialTalkList
	 * @return 	if the value is greater than 0, it represents the index of the value in given list
	 * 			if the value is -1, then it means the exact single talk data cannot be found, but there is possibility still
	 * 			if the value is -2, then it means that it is impossible to find one or more talk data to fit the remaining minutes
	 */
	private static int discoverRemainingTalk(int remainingMin, List<Talk> initialTalkList) {
		int possibility = -2;
		for(Talk talk : initialTalkList) {
			if( !talk.isPlanned() ) {
				if( remainingMin == talk.getDuration() ) {
					return initialTalkList.indexOf(talk); // index
				} else if(talk.getDuration() < remainingMin) {
					possibility = -1;
				}
			}
		}
		return possibility;
	}
	
	private static Talk generateTalkFromLine(String line) {
		return new Talk(filterTalkTitle(line), TalkType.findTalkType(line), findTalkDuration(line)); 
	}
	
	private static String filterTalkTitle(String title) {
		return title.replaceAll("[0-9]", EMPTY)
				.replace(TalkType.MINUTES.value(), EMPTY)
				.replace(TalkType.LIGTNING.value(), EMPTY).trim();
	} 
	
	private static int findTalkDuration(String line) {
		if(line.contains(TalkType.LIGTNING.value())) {
			return CTMConstants.LIGHTING_DURATION;
		}
		
		return Integer.valueOf(line.replaceAll("\\D+","")).intValue();
	}
}
