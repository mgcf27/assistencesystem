package com.miguel.assistencesystem.application.dto.response;

import com.miguel.assistencesystem.domain.security.EmployeeAccount;
import com.miguel.assistencesystem.domain.security.EmployeeRole;

public record EmployeeResponseDTO(
		Long id,
		String name,
		String email,
		String cpf,
		String phone,
		EmployeeRole role,
		boolean active) {
	public static EmployeeResponseDTO fromEntity(EmployeeAccount employee) {
		return new EmployeeResponseDTO(
				employee.getId(),
				employee.getName(),
				employee.getEmail(),
				employee.getCpf(),
				employee.getPhone(),
				employee.getEmployeeRole(),
				employee.isActive());
	}

}
