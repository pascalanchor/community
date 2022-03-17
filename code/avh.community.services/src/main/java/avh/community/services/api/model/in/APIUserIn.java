package avh.community.services.api.model.in;

import avh.community.services.api.model.APIUser;

public class APIUserIn extends APIUser {
	private String password;
	
	public APIUserIn() {}
	public APIUserIn(String ml, String fn, String ln, String pwd) {
		super(ml, fn, ln);
		this.password = pwd;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
