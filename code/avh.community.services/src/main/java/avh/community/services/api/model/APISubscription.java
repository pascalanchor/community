package avh.community.services.api.model;


public class APISubscription {

	private String user_email;
	private String channel_id;
	
	public APISubscription() {
	}
	

	public APISubscription(String user_email, String channel_id) {
		this.user_email = user_email;
		this.channel_id = channel_id;
		
		
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}


	

	
	
	
}
