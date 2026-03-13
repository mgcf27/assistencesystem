package com.miguel.assistencesystem.infrastructure.security.session;

import java.time.LocalDateTime;

import com.miguel.assistencesystem.domain.security.EmployeeRole;

public record AuthenticatedSessionView(
		Long employeeId,
		String email,
		EmployeeRole role,
		String token,
		LocalDateTime expiresAt,
		boolean revoked) {}
