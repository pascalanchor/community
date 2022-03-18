package avh.community.services.logic;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import avh.community.model.Credentials;
import avh.community.model.Token;
import avh.community.model.User;
import avh.community.services.api.errors.BusinessException;
import avh.community.services.logic.security.AuthorizationManager;
import avh.community.services.logic.security.PasswordHasher;
import avh.community.services.persistence.ModelRepo;

@Component
public class UserControllerImpl {
	public static final long TokenDuration = 1 * 60 * 60 * 1000;
	
	@Autowired private ModelRepo rep;
	@Autowired private PasswordHasher pHasher;
	@Autowired private AuthorizationManager amgr;
	
	@Transactional
	public User registerUser(User u, String pwd) {
		Optional<User> ou = rep.getUserRepo().findById(u.getEmail());
		if (ou.isPresent())
			throw new BusinessException(String.format("the user %s is already registered", u.getEmail()));
		
		
		Credentials cr = new Credentials();
		cr.setEid(UUID.randomUUID().toString());
		cr.setUser(u);
		String hpwd = pHasher.hashPassword(pwd);
		cr.setPwd(hpwd);
		rep.getUserRepo().save(u);
		rep.getCredRepo().save(cr);
		return u;
	}
	
	@Transactional
	public Token loginUser(String email, String pwd) {
		User u = getUser(email);
		
		List<Credentials> crl = rep.getCredRepo().findByUser(u);
		if ((crl == null) || (crl.size() <= 0))
			throw new BusinessException(String.format("cannot authenticate the user %s. No credentials defined", email));	
		Credentials cr = crl.get(0);
		
		if (!pHasher.match(pwd, cr.getPwd()))
			throw new BusinessException(String.format("wrong password"));

		rep.getTokenRepo().deleteAllByUser(u);
		Token t = new Token();
		t.setEid(UUID.randomUUID().toString());
		long tdate = System.currentTimeMillis();
		t.setTokenDate(new Timestamp(tdate));
		t.setExpiryDate(new Timestamp(tdate + UserControllerImpl.TokenDuration));
		t.setUser(u);
		rep.getTokenRepo().save(t);
		
		return t;
	}
	
	public Token renewToken(String email, String token) {
		User u = getUser(email);
				
		Optional<Token> tk = rep.getTokenRepo().findById(token);
		if (tk.isEmpty())
			throw new BusinessException(String.format("the token %s doesn't exist", token));
		Token t = tk.get();
		
		if (u.getEmail().equals(t.getUser().getEmail())) {
			t.setExpiryDate(new Timestamp(System.currentTimeMillis() + UserControllerImpl.TokenDuration));
			rep.getTokenRepo().save(t);
			return t;
		} else {
			throw new BusinessException(String.format("the token %s isn't defined for the user %s", token, email));
		}
	}
	
	public boolean updateUserPassword(String token, String email, String oldP, String newP) {
		User u = getUser(email);
		
		validateToken(token, u);
		
		List<Credentials> crl = rep.getCredRepo().findByUser(u);
		if ((crl == null) || (crl.size() <= 0))
			throw new BusinessException(String.format("cannot authenticate the user %s. No credentials defined", email));	
		Credentials cr = crl.get(0);
		
		if (!pHasher.match(oldP, cr.getPwd()))
			throw new BusinessException(String.format("wrong old password"));
		
		String hpwd = pHasher.hashPassword(newP);
		cr.setPwd(hpwd);
		rep.getCredRepo().save(cr);
		return true;
	}
	
	public User getUserByToken(String token) {
		Token tk = amgr.getToken(token);
		return tk.getUser();
	}
	
	private boolean validateToken(String token, User u) {
		Token tk = amgr.getToken(token);
		if (tk.getUser().getEmail().equals(u.getEmail()))
			return true;
		else
			throw new BusinessException(String.format("token %s not valid for user %s", token, u.getEmail()));
	}
	
	private User getUser(String email) {
		Optional<User> ou = rep.getUserRepo().findById(email);
		if (ou.isEmpty())
			throw new BusinessException(String.format("the user %s doesn't exist", email));
		return ou.get();
	}
}
