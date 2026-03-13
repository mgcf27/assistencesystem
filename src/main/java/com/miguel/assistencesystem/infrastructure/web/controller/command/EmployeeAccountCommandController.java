package com.miguel.assistencesystem.infrastructure.web.controller.command;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.assistencesystem.application.dto.command.EmployeeCreateDTO;
import com.miguel.assistencesystem.application.dto.response.EmployeeResponseDTO;
import com.miguel.assistencesystem.application.security.EmployeeAccountService;

@RestController
@RequestMapping("/employees")

public class EmployeeAccountCommandController {
	private final EmployeeAccountService employeeService;
	
	public EmployeeAccountCommandController(EmployeeAccountService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping 
	@ResponseStatus(HttpStatus.CREATED)
	public  EmployeeResponseDTO createEmployee(
			@RequestBody EmployeeCreateDTO employeeCreateDto) {
		return employeeService.create(employeeCreateDto);
	}
	
}
