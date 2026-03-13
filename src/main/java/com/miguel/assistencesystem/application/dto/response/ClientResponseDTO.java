package com.miguel.assistencesystem.application.dto.response;

import com.miguel.assistencesystem.domain.model.Client;

public record ClientResponseDTO(
	    Long id,
	    String name,
	    String cpf,
	    String phone,
	    String address,
	    String email
	) {
	    public static ClientResponseDTO fromEntity(Client client) {
	        return new ClientResponseDTO(
	            client.getId(),
	            client.getName(),
	            client.getCpf(),
	            client.getPhone(),
	            client.getAddress(),
	            client.getEmail()
	        );
	    }
	}
