package com.miguel.assistencesystem.infrastructure.security.session.cleanup;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.infrastructure.persistence.AuthenticationTokenDAO;

@Component
public class AuthenticationTokenCleanupJob {

    private final AuthenticationTokenDAO tokenDAO;

    public AuthenticationTokenCleanupJob(AuthenticationTokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void cleanupExpiredSessions() {

        int removed = tokenDAO.deleteExpiredOrRevoked(LocalDateTime.now());

        if (removed > 0) {
            System.out.println("Authentication cleanup removed " + removed + " expired sessions");
        }
    }
}