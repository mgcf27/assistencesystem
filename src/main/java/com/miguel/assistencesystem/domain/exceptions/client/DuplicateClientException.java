package com.miguel.assistencesystem.domain.exceptions.client;

import java.util.List;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;

@SuppressWarnings("serial")
public class DuplicateClientException extends ConflictException {
	private final List<String> violations;
	
	public DuplicateClientException(List<String> violations) {
		super(
				DomainErrorCode.DUPLICATE_CLIENT,
				"Duplicate Cliente Data: " + String.join("; ", violations));
		this.violations = violations;
	}

	public List<String> getViolations() {
        return violations;
    }
}
