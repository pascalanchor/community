package avh.community.services.logic.domains;

public enum EChannelScope {
	Public("Public"),
	Private("Private");
	
	private String label;
	
	private EChannelScope(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static EChannelScope fromString(String lb) {
		for (EChannelScope res : EChannelScope.values())
			if (res.getLabel().equals(lb))
				return res;
		return null;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
