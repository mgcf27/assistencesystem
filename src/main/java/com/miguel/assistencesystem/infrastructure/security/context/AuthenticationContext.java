package com.miguel.assistencesystem.infrastructure.security.context;

import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

public final class AuthenticationContext {

    private static final ThreadLocal<AuthenticatedIdentity> CURRENT = new ThreadLocal<>();

    private AuthenticationContext() {}

    public static void set(AuthenticatedIdentity identity) {
        CURRENT.set(identity);
    }

    public static AuthenticatedIdentity get() {
        return CURRENT.get();
    }

    public static void clear() {
        CURRENT.remove();
    }

}

