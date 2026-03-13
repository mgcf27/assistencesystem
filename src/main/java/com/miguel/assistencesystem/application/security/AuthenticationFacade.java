package com.miguel.assistencesystem.application.security;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.miguel.assistencesystem.domain.exceptions.authentication.UnauthenticatedException;
import com.miguel.assistencesystem.infrastructure.security.context.AuthenticationContext;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

@Component
public final class AuthenticationFacade {

    public AuthenticatedIdentity requireAuthenticated() {

        AuthenticatedIdentity identity = AuthenticationContext.get();

        if (identity == null) {
            throw new UnauthenticatedException(
                "No authenticated identity present in current context"
            );
        }

        return identity;
    }

    public Optional<AuthenticatedIdentity> getCurrentIdentity() {
        return Optional.ofNullable(AuthenticationContext.get());
    }

}
