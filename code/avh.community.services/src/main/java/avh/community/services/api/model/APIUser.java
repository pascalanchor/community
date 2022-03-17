package avh.community.services.api.model;

public class APIUser {
	private String email;
	private String firstname;
	private String lastname;
	
	public APIUser() {}
	public APIUser(String ml, String fn, String ln) {
		this.email = ml;
		this.firstname = fn;
		this.lastname = ln;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
