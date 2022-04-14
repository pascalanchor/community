package avh.community.services.api.model;

public class APIMessage {

	private String body;
	private int stars;
	
	public APIMessage() {
		
	}
	
	public APIMessage(String body, int stars) {
		super();
		this.body = body;		
		this.stars = stars;
	}



	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}
	
	
}
