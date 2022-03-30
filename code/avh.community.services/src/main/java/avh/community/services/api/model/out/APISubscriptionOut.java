package avh.community.services.api.model.out;

import avh.community.services.api.model.APISubscription;

public class APISubscriptionOut extends APISubscription {
	private String status;
	
	public APISubscriptionOut()
	{
		
	}
	
	public APISubscriptionOut(String user_email,String channel_id,String status)
	{
		super(user_email,channel_id);
		this.status=status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
