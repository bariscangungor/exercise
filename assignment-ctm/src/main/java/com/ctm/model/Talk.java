package com.ctm.model;

import com.ctm.type.SessionType;
import com.ctm.type.TalkType;

import static com.ctm.constants.CTMConstants.*;

/**
 * @author bgungor
 *
 */
public class Talk implements Comparable<Talk> {

	private String title;

	private TalkType talkType;
	private SessionType sessionType;

	private int duration;

	private boolean planned = false;

	public Talk() {
	}

	/**
	 * @param title
	 * @param talkType
	 */
	public Talk(String title, TalkType talkType, int duration) {
		super();
		this.title = title;
		this.talkType = talkType;
		this.duration = duration;
	}

	public String getDurationForPrintOut() {
		return TalkType.LIGTNING.equals(this.talkType) ? this.talkType.value()
				: this.duration + TalkType.MINUTES.value();
	}

	/**
	 * @return the talkType
	 */
	public TalkType getTalkType() {
		return talkType;
	}

	/**
	 * @param talkType
	 *            the talkType to set
	 */
	public void setTalkType(TalkType talkType) {
		this.talkType = talkType;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the sessionType
	 */
	public SessionType getSessionType() {
		return sessionType;
	}

	/**
	 * @param sessionType
	 *            the sessionType to set
	 */
	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}

	/**
	 * @return the planned
	 */
	public boolean isPlanned() {
		return planned;
	}

	/**
	 * @param planned
	 *            the planned to set
	 */
	public void setPlanned(boolean planned) {
		this.planned = planned;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Talk [title=" + title + ", talkType=" + talkType
				+ ", sessionType=" + sessionType + ", duration=" + duration
				+ ", planned=" + planned + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Talk t) {
		if (this.duration == t.duration) {
			return EQUAL;
		} else if (this.duration < t.duration) {
			return BEFORE;
		} else {
			return AFTER;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duration;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Talk other = (Talk) obj;
		if (duration != other.duration)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
