/**
 * 
 */
package com.ctm.core;

import static com.ctm.constants.CTMConstants.AFTERNOON_TOTAL;
import static com.ctm.constants.CTMConstants.INPUT_FILE;
import static com.ctm.constants.CTMConstants.LOGGING_FILE;
import static com.ctm.constants.CTMConstants.MORNING_TOTAL;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import com.ctm.exceptions.CTMBusinessException;
import com.ctm.exceptions.CTMRuntimeException;
import com.ctm.model.impl.Talk;
import com.ctm.type.SessionType;
import com.ctm.utils.CTMPlanningUtil;
import com.ctm.utils.CTMTalkUtil;

/**
 * @author bgungor
 *
 */
public class CTMApplication {
	private static final Logger LOG = Logger.getLogger(CTMApplication.class.getName());
	
	public static void main(String[] args) {  
		CTMApplication ctm = new CTMApplication();
		 
		ctm.planTalks();
		// we can plan other plannable items here
	}
	 
	@PostConstruct
	private void init() {
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream(LOGGING_FILE));
		} catch (SecurityException | IOException e) {
			LOG.log(Level.SEVERE, "Please check logging conf. file and conf. details!", e);
		} 
	}
	
	private void planTalks() {
		CTMPlanningUtil<Talk> planningUtil = new CTMPlanningUtil<>();
		
		try {
			List<Talk> talkList = CTMTalkUtil.generateTalkListFromResource(INPUT_FILE); 
			
			List<Talk> track1MorningSession = planningUtil.planSessionByParams(talkList, MORNING_TOTAL, SessionType.MORNING);
			List<Talk> track1AfternoonSession = planningUtil.planSessionByParams(talkList, AFTERNOON_TOTAL, SessionType.AFTERNOON);

			List<Talk> track2MorningSession = planningUtil.planSessionByParams(talkList, MORNING_TOTAL, SessionType.MORNING);
			List<Talk> track2AfternoonSession = planningUtil.planSessionByParams(talkList, AFTERNOON_TOTAL, SessionType.AFTERNOON);
			
			/**
			 * printing Track1 
			 */
			System.err.println("Track 1:");
			CTMTalkUtil.printMorningTalks(track1MorningSession); 
			CTMTalkUtil.printAfternoonTalks(track1AfternoonSession);	

			/**
			 * printing Track2 
			 */
			System.err.println("\nTrack 2:");
			CTMTalkUtil.printMorningTalks(track2MorningSession);
			CTMTalkUtil.printAfternoonTalks(track2AfternoonSession);	
			
		} catch (CTMBusinessException e) { 
			LOG.log(Level.parse(e.getErrorCode()), e.getMessage());
		} catch (CTMRuntimeException e) { 
			LOG.log(Level.parse(e.getErrorCode()), e.getMessage(), e);
		} 
	}
	
}
