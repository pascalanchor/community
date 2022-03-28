package avh.community.services.api.model.out;

import avh.community.services.api.model.APIChannelThread;

public class APIChannelThreadOut extends APIChannelThread{
	private long creationDate;
	private APISubscriptionOut subscription;
	
	public APIChannelThreadOut() {}
	
	
	
	public APIChannelThreadOut(String keywords, String subject,long creationDate, APISubscriptionOut subscription) {
		super(keywords, subject);
		this.creationDate = creationDate;
		this.subscription = subscription;
	}



	public long getCreationDate() {
		return creationDate;
	}



	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}



	public APISubscriptionOut getSubscription() {
		return subscription;
	}



	public void setSubscription(APISubscriptionOut subscription) {
		this.subscription = subscription;
	}

	
	



	
	
}
