package avh.community.services.api.model.out;

import avh.community.services.api.model.APIUser;

public class APIUserOut extends APIUser {
	public APIUserOut() {}
	
	public APIUserOut(String ml, String fn, String ln) {
		super(ml, fn, ln);
	}
}
