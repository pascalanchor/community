package avh.community.services.api.model;

public class APIChannel {
	private String name;
	private String description;
	private String scope;
	
	public APIChannel() {}
	public APIChannel(String nm, String desc, String scope) {
		this.name = nm;
		this.description = desc;
		this.scope = scope;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
}
