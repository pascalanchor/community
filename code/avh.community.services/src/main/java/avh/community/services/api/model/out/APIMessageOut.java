package avh.community.services.api.model.out;

import java.sql.Timestamp;

import avh.community.services.api.model.APIMessage;

public class APIMessageOut extends APIMessage{

	private String id;
	private Timestamp creationDate;
	private APIChannelThreadOut channelThread;
	private APISubscriptionOut Subscription;
	private String reply_to; 
	
	
	public APIMessageOut(String id,
			String body,
			int stars,
			Timestamp creationDate,
			String reply_to,
			APIChannelThreadOut channelThread,
			APISubscriptionOut sbo) {
		
		super(body,stars);
		this.id=id;
		this.creationDate=creationDate;
		this.channelThread=channelThread;
		this.Subscription=sbo;
		this.reply_to=reply_to;
	}
	
	public APIMessageOut() {
		
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public APIChannelThreadOut getChannelThread() {
		return channelThread;
	}

	public void setChannelThread(APIChannelThreadOut channelThread) {
		this.channelThread = channelThread;
	}

	public APISubscriptionOut getSubscription() {
		return Subscription;
	}

	public void setSubscription(APISubscriptionOut sbo) {
		this.Subscription = sbo;
	}

	public String getReply_to() {
		return reply_to;
	}

	public void setReply_to(String reply_to) {
		this.reply_to = reply_to;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
