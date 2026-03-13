package com.miguel.assistencesystem.infrastructure.security.credential;

import com.miguel.assistencesystem.domain.security.AuthenticationFailureReason;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

public final class AuthenticationResult {

    private final AuthenticatedIdentity identity;
    private final AuthenticationFailureReason failureReason;

    private AuthenticationResult(
            AuthenticatedIdentity identity,
            AuthenticationFailureReason failureReason
    ) {
        this.identity = identity;
        this.failureReason = failureReason;
    }

    public static AuthenticationResult success(
            AuthenticatedIdentity identity
    ) {
        return new AuthenticationResult(identity, null);
    }

    public static AuthenticationResult failure(
            AuthenticationFailureReason reason
    ) {
        return new AuthenticationResult(null, reason);
    }

    public boolean isAuthenticated() {
        return identity != null;
    }

    public AuthenticatedIdentity getIdentity() {
        return identity;
    }

    public AuthenticationFailureReason getFailureReason() {
        return failureReason;
    }
}