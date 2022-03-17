package avh.community.services.api.model.out;

import avh.community.services.api.model.APIToken;

public class APITokenOut extends APIToken {
	private String email;
	private long tfrom;
	private long tto;
	
	public APITokenOut() {}
	public APITokenOut(String tk, String email, long fr, long to) {
		super(tk);
		this.email = email;
		tfrom = fr;
		tto = to;
	}

	public String getEmail() { return this.email; }
	public void setEmail(String email) { this.email = email; }
	
	public long getTfrom() {
		return tfrom;
	}
	public void setTfrom(long tfrom) {
		this.tfrom = tfrom;
	}
	public long getTto() {
		return tto;
	}
	public void setTto(long tto) {
		this.tto = tto;
	}
}
