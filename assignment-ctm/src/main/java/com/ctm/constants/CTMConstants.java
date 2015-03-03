/**
 * 
 */
package com.ctm.constants;

/**
 * @author bgungor
 *
 */
public class CTMConstants {
	public static final String EMPTY = "";
	
	public static final String LUNCH_PRINT_OUT = "12:00PM Lunch";
	public static final String NETWORKING_PRINT_OUT = " Networking Event";
	
	public static final String TALK_DATE_PRINT_FORMAT 	= "hh:mma";
	public static final String TALK_DATE_FORMAT 		= "hh:mm";
//	public static final String TALK_DATE_PRINT_FORMAT 	= "HH:mma";
//	public static final String TALK_DATE_FORMAT 		= "HH:mm";
	
	public static final String TALK_START_MORNING 	= "09:00";
	public static final String TALK_START_AFTERNOON = "01:00PM";
 
	public static final String INPUT_FILE = "/input.txt";
	public static final String LOGGING_FILE = "/logging.properties"; 

	public static final int MORNING_START_TIME_HOUR   = 9;
	public static final int AFTERNOON_START_TIME_HOUR = 13;
	public static final int LUNCH_TIME_HOUR = 12; // MORNING_END_TIME
	
	public static final String MORNING_START_TIME 	= "09:00AM"; 
	public static final String AFTERNOON_START_TIME = "01:00PM";  
	public static final String LUNCH_TIME = "12:00PM"; // MORNING_END_TIME
	
	public static final int LIGHTING_DURATION = 5;

	public static final int MORNING_TOTAL 	= 180;
	public static final int AFTERNOON_TOTAL = 240;
	public static final int AFTERNOON_GAP 	= 60;

	public static final int BEFORE = -1;
	public static final int EQUAL = 0;
	public static final int AFTER = 1;
 
}
