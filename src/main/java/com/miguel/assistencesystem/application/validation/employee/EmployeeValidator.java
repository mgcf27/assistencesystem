package com.miguel.assistencesystem.application.validation.employee;

import java.util.ArrayList;
import java.util.List;

import com.miguel.assistencesystem.application.dto.command.EmployeeCreateDTO;
import com.miguel.assistencesystem.application.validation.CpfValidator;
import com.miguel.assistencesystem.application.validation.EmailValidator;
import com.miguel.assistencesystem.application.validation.PhoneValidator;
import com.miguel.assistencesystem.domain.exceptions.employee.DuplicateEmployeeException;
import com.miguel.assistencesystem.domain.exceptions.employee.InvalidEmployeeDataException;
import com.miguel.assistencesystem.infrastructure.security.persistence.EmployeeAccountDAO;

public class EmployeeValidator {
	private EmployeeValidator() {}
	
	public static void isValidInput(EmployeeCreateDTO employeeCreateDto){
		List<String> errors = new ArrayList<>();
		
		if (!EmailValidator.isValid(employeeCreateDto.getEmail()))
            errors.add("Email is invalid");
		
		if(!PasswordValidator.isValid(employeeCreateDto.getPassword())) {
			errors.add("Password is invalid");
		}
		
		if (employeeCreateDto.getName() == null || employeeCreateDto.getName().isBlank())
            errors.add("Name is required");
		
		if (!CpfValidator.isValid(employeeCreateDto.getCpf()))
            errors.add("CPF is invalid");
		
		if (!PhoneValidator.isValid(employeeCreateDto.getPhone()))
            errors.add("Phone number is invalid");
		

		if (employeeCreateDto.getAddress()== null || employeeCreateDto.getAddress().isBlank()) {
			errors.add("Address is required");
		}
		
		if(employeeCreateDto.getRole() == null) {
			errors.add("Role is required");
		}
		
		if(!errors.isEmpty()){
			throw new InvalidEmployeeDataException(errors);
		}
	}
	
	public static void validateUniqueness(
			EmployeeCreateDTO employeeCreateDto,
			EmployeeAccountDAO employeeDAO) {
		List<String> uniquenessViolations = new ArrayList<>();
		
		if(employeeDAO.existsByEmail(employeeCreateDto.getEmail())) {
			uniquenessViolations.add("Email is already registered");
		}
		
		if(employeeDAO.existsByCpf(employeeCreateDto.getCpf())) {
			uniquenessViolations.add("Cpf is already registered");
		}
		
		if(employeeDAO.existsByPhone(employeeCreateDto.getPhone())) {
			uniquenessViolations.add("Phone is already registered");
		}
		
		if(!uniquenessViolations.isEmpty()) {
			throw new DuplicateEmployeeException(uniquenessViolations);
		}
		
	}
	

}
