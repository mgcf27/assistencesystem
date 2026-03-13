package com.miguel.assistencesystem.application.dto.view;

import com.miguel.assistencesystem.domain.model.Client;

public class ClientForSoViewDTO {
	private final Long id;
	private final String name;
	private final String cpf;
	private final String phone;
	private final String address;
	private final String email;
	
	public ClientForSoViewDTO(
			Long id,
			String name,
			String cpf,
			String phone,
			String address,
			String email
	) {
		this.id = id;
		this.name = name;
		this.cpf  = cpf;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}
	
	public Long getId() { return id; }
	public String getName() { return name; }
	public String getCPF() { return cpf; }
	public String getPhone() { return phone; }
	public String getAddress() { return address; }
	public String getEmail() { return email; }
	
	public static ClientForSoViewDTO fromEntity(Client client) {
		return new ClientForSoViewDTO(
				client.getId(),
				client.getName(),
				client.getCpf(),
				client.getPhone(),
				client.getAddress(),
				client.getEmail()
				);
	}
}
