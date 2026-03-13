package com.miguel.assistencesystem.application.dto.command;

public class ClientRegistrationDTO {
	private String name;
	private String cpf;
	private String phone;
	private String address;
	private String email;
	
	
	public String getName() {return name;}
	public String getCpf() {return cpf;}
	public String getPhone() {return phone;}
	public String getAddress() {return address;}
	public String getEmail() {return email;}
	
	public void setName(String name) {this.name = name;}
	public void setCpf(String cpf) {this.cpf = cpf;}
	public void setPhone(String phone) {this.phone = phone;}
	public void setAddress(String address) {this.address = address;}
	public void setEmail(String email) {this.email = email;}
	
	public ClientRegistrationDTO () {}
	
	public ClientRegistrationDTO (
			String name,
			String cpf, 
			String phone,
			String address,
			String email
			) {
		this.name = name;
		this.cpf = cpf;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}

}
