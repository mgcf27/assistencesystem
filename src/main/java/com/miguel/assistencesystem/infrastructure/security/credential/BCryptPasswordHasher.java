package com.miguel.assistencesystem.infrastructure.security.credential;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class BCryptPasswordHasher implements PasswordHasher {
	private final BCryptPasswordEncoder encoder;
	
	public BCryptPasswordHasher() {
        this.encoder = new BCryptPasswordEncoder();
    }
	
	@Override
	public String hash(String rawPassword) {
		return encoder.encode(rawPassword);
	}
	
	@Override
	public boolean matches(String rawPassword, String storedHash) {
		return encoder.matches(rawPassword, storedHash);
	}
}
