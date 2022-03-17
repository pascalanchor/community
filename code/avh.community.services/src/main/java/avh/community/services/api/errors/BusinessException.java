package avh.community.services.api.errors;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -4317307318079321456L;

	public BusinessException(String msg) {
		super(msg);
	}
}
