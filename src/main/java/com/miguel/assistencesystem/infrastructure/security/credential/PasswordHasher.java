package com.miguel.assistencesystem.infrastructure.security.credential;

public interface PasswordHasher {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String storedHash);
}
