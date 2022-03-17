package avh.community.services.api.util;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {
	private final Pbkdf2PasswordEncoder pHasher = new Pbkdf2PasswordEncoder("Lorem Ipsum", 10, 32);

	public PasswordHasher() {}

	public String hashPassword(String raw) {
		return pHasher.encode(raw);
	}
	
	public boolean match(String raw, String hashedPassword) {
		return pHasher.matches(raw, hashedPassword);
	}
}
