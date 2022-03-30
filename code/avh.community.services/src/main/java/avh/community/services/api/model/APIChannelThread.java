package avh.community.services.api.model;

import avh.community.services.api.model.in.APISubscriptionIn;

public class APIChannelThread {

	private String keywords;
	private String subject;
	
	public APIChannelThread() {}
	
	public APIChannelThread(String keywords, String subject) {
		this.keywords = keywords;
		this.subject = subject;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	
	
	
	
}
