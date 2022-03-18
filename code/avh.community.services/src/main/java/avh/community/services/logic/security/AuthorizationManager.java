package avh.community.services.logic.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.community.model.Token;
import avh.community.model.User;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.logic.domains.EFeature;
import avh.community.services.persistence.ModelRepo;

@Component
public class AuthorizationManager {
	@Autowired private ModelRepo rep;
	
	public Token isAuthorized(String tk, EFeature f) {
		Token token = getToken(tk);
		// check here user, group, and features
		return token;
	}
	
	public Token getToken(String token) {
		Optional<Token> ot = rep.getTokenRepo().findById(token);
		if (ot.isEmpty())
			throw new BusinessException(String.format("invalid token %s", token));
		Token tk = ot.get();
		if (tk.getExpiryDate().getTime() < System.currentTimeMillis())
			throw new BusinessException(String.format("token %s already expired", token));
		return tk;
	}
}
