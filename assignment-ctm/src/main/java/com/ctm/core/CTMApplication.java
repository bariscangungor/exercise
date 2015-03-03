/**
 * 
 */
package com.ctm.core;

import static com.ctm.constants.CTMConstants.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import com.ctm.exceptions.CTMBusinessException;
import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.model.Talk;
import com.ctm.type.SessionType;
import com.ctm.utils.CTMTalkUtil;

/**
 * @author bgungor
 *
 */
public class CTMApplication {
	private static final Logger LOG = Logger.getLogger(CTMApplication.class.getName());
	
	public static void main(String[] args) {  
		new CTMApplication().execute();
	}
	 
	@PostConstruct
	private void init() throws SecurityException, FileNotFoundException, IOException {
		LogManager.getLogManager().readConfiguration(new FileInputStream(LOGGING_FILE));
	}
	
	private void execute() {
		List<Talk> initialTalkList = new ArrayList<>();
		
		try {
			initialTalkList = CTMTalkUtil.generateTalkListFromResource(INPUT_FILE);
			
			List<Talk> track1MorningSession = CTMTalkUtil.planSessionByParams(initialTalkList, MORNING_TOTAL, SessionType.MORNING);
			List<Talk> track1AfternoonSession = CTMTalkUtil.planSessionByParams(initialTalkList, AFTERNOON_TOTAL, SessionType.AFTERNOON);

			List<Talk> track2MorningSession = CTMTalkUtil.planSessionByParams(initialTalkList, MORNING_TOTAL, SessionType.MORNING);
			List<Talk> track2AfternoonSession = CTMTalkUtil.planSessionByParams(initialTalkList, AFTERNOON_TOTAL, SessionType.AFTERNOON);
			
			/**
			 * printing Track1 
			 */
			System.err.println("Track 1:");
			CTMTalkUtil.printMorningSession(track1MorningSession); 
			CTMTalkUtil.printAfternoonSession(track1AfternoonSession);	

			/**
			 * printing Track2 
			 */
			System.err.println("\nTrack 2:");
			CTMTalkUtil.printMorningSession(track2MorningSession);
			CTMTalkUtil.printAfternoonSession(track2AfternoonSession);	
			
		} catch (CTMBusinessException e) { 
			LOG.log(Level.parse(e.getErrorCode()), e.getMessage());
		} catch (CTMRuntimeException e) { 
			LOG.log(Level.parse(e.getErrorCode()), e.getMessage(), e);
		} 
	}
	
}
