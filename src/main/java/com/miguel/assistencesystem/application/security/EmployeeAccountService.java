package com.miguel.assistencesystem.application.security;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.audit.AuditService;
import com.miguel.assistencesystem.application.dto.command.EmployeeCreateDTO;
import com.miguel.assistencesystem.application.dto.response.EmployeeResponseDTO;
import com.miguel.assistencesystem.application.validation.employee.EmployeeValidator;
import com.miguel.assistencesystem.domain.audit.AuditAction;
import com.miguel.assistencesystem.domain.audit.EntityType;
import com.miguel.assistencesystem.domain.exceptions.employee.InsufficientPermissionsException;
import com.miguel.assistencesystem.domain.security.EmployeeAccount;
import com.miguel.assistencesystem.domain.security.EmployeeRole;
import com.miguel.assistencesystem.infrastructure.persistence.EmployeeAccountDAO;
import com.miguel.assistencesystem.infrastructure.security.credential.PasswordHasher;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

@Service
@Transactional
public class EmployeeAccountService {
	
	private final EmployeeAccountDAO employeeDAO;
	private final AuthenticationFacade authentication;
	private final PasswordHasher passwordHasher;
	private final AuditService auditService;
	
	public EmployeeAccountService(
			EmployeeAccountDAO employeeDAO,
			AuthenticationFacade authentication,
			PasswordHasher passwordHasher,
			AuditService auditService
			) {
		this.employeeDAO = employeeDAO;
		this.authentication = authentication;
		this.passwordHasher = passwordHasher;
		this.auditService = auditService;
	}

	public EmployeeResponseDTO create(EmployeeCreateDTO employeeCreateDto) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		if(identity.getRole() != EmployeeRole.ADMIN) {
			throw new InsufficientPermissionsException(
					"You do not have permission to create employees");
		}
		
		EmployeeValidator.isValidInput(employeeCreateDto);
	
		EmployeeValidator.validateUniqueness(employeeCreateDto, employeeDAO);
		
		String hashedPassword = passwordHasher.hash(employeeCreateDto.getPassword());
	
		EmployeeAccount newEmployee = EmployeeAccount.create(
				employeeCreateDto.getEmail(),
				hashedPassword,
				employeeCreateDto.getName(),
				employeeCreateDto.getCpf(),
				employeeCreateDto.getPhone(),
				employeeCreateDto.getAddress(),
				employeeCreateDto.getRole()
				);
		
		employeeDAO.persist(newEmployee);
		
		auditService.record(
				AuditAction.EMPLOYEE_ACCOUNT_CREATED,
				EntityType.EMPLOYEE_ACCOUNT,
				newEmployee.getId());
		
		return EmployeeResponseDTO.fromEntity(newEmployee);
	}
	
}
