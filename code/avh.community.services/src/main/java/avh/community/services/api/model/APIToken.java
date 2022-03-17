package avh.community.services.api.model;

public class APIToken {
	private String token;
	
	public APIToken() {}
	public APIToken(String tk) {
		this.token = tk;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
