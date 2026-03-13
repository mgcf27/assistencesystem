package com.miguel.assistencesystem.application.dto.command;

import com.miguel.assistencesystem.domain.security.EmployeeRole;

public class EmployeeCreateDTO {
	private String email;
	private String password;
	private String name;
	private String cpf;
	private String phone;
	private String address;
	private EmployeeRole role;
	
	public EmployeeCreateDTO() {}
	
	public EmployeeCreateDTO(
			String email,
			String password,
			String name,
			String cpf,
			String phone,
			String address,
			EmployeeRole role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.cpf = cpf;
		this.phone = phone;
		this.address = address;
		this.role = role;
	}

	public String getEmail() {return email;}
	public String getPassword() {return password;}
	public String getName() {return name;}
	public String getCpf() {return cpf;}
	public String getPhone() {return phone;}
	public String getAddress() {return address;}
	public EmployeeRole getRole() {return role;}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRole(EmployeeRole role) {
		this.role = role;
	}

}
