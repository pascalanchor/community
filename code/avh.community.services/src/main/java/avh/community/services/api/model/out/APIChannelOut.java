package avh.community.services.api.model.out;

import avh.community.services.api.model.APIChannel;

public class APIChannelOut extends APIChannel {
	private String id;
	private long creationDate;

	
	public APIChannelOut() {}

	public APIChannelOut(String nm, String desc, String sc, String id, long ts) {
		super(nm, desc, sc);
		this.id = id;
		this.creationDate = ts;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
}
