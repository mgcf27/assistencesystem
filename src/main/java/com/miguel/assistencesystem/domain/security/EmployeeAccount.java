package com.miguel.assistencesystem.domain.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;

@Entity
@Table(name = "employee_accounts",
uniqueConstraints = @UniqueConstraint(name = "uk_employee_accounts_email", columnNames = "email"))
public class EmployeeAccount {
	
	//Credentials
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 100, updatable = false)
	private String email;
	
	@Column(nullable = false)
	private String passwordHash;
	
	@Column(nullable = false)
	private boolean active;
	
	//profile
	@Column(nullable = false, length = 225)
	private String name;
	
	@Column(nullable = false, unique = true, length = 14, updatable = false)
	private String cpf;
	
	@Column(nullable = false,unique = true, length = 20)
	private String phone;

	@Column(nullable = false, length = 200)
	private String address;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EmployeeRole role;
	
	@Version
	private Long version;
	
	protected EmployeeAccount () {}
	
	public static EmployeeAccount create(
			String email,
			String passwordHash,
			String name,
			String cpf,
			String phone,
			String address,
			EmployeeRole role
			) {
		EmployeeAccount ea = new EmployeeAccount();
		
		ea.email = email;
		ea.passwordHash = passwordHash;
		ea.active = true;
		ea.name = name;
		ea.cpf = cpf;
		ea.phone = phone;
		ea.address = address;
		ea.role = role;
		return ea;
	}
	
	public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
    	return name;
    }

    public String getCpf() {
    	return cpf;
    }

    public String getPhone() {
    	return phone;
    }
    
    public String getAddress() {
    	return address;
    }
    
    public EmployeeRole getEmployeeRole() {
    	return role;
    }
}
