package avh.community.services.logic.domains;

public enum ESubscriptionStatus {

	active("active"),
	suspended("suspended"),
	pending("pending"),
	closed("closed");
	
	private String label;
	
	private ESubscriptionStatus()
	{
		
	}
	
	private ESubscriptionStatus(String label)
	{
		this.label=label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static ESubscriptionStatus fromString(String lb) {
		for (ESubscriptionStatus res : ESubscriptionStatus.values())
			if (res.getLabel().equals(lb))
				return res;
		return null;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
