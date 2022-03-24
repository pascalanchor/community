package avh.community.services.api.model.in;

import avh.community.services.api.model.APISubscription;

public class APISubscriptionIn extends APISubscription {

	public APISubscriptionIn()
	{
		
	}
	
	public APISubscriptionIn(String user_email,String channel_id)
	{
		super(user_email,channel_id);
	}
}
