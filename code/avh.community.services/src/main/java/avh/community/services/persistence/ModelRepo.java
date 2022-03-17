package avh.community.services.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelRepo {
	@Autowired private UserRepo urep;
	@Autowired private CredentialsRepo crep;
	@Autowired private TokenRepo trep;
	
	public ModelRepo() {}
	
	public UserRepo getUserRepo() {
		return this.urep;
	}
	
	public CredentialsRepo getCredRepo() {
		return this.crep;
	}
	
	public TokenRepo getTokenRepo() {
		return this.trep;
	}
	
}
