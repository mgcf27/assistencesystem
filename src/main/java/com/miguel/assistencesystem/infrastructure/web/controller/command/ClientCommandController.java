package com.miguel.assistencesystem.infrastructure.web.controller.command;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.assistencesystem.application.command.ClientService;
import com.miguel.assistencesystem.application.dto.command.ClientRegistrationDTO;
import com.miguel.assistencesystem.application.dto.command.ClientUpdateDTO;
import com.miguel.assistencesystem.application.dto.response.ClientResponseDTO;

@RestController
@RequestMapping("/clients")
public class ClientCommandController {
	
	private final ClientService clientService;
	
	public ClientCommandController(ClientService clientService) {
		this.clientService = clientService;
	}

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDTO registerClient(@RequestBody ClientRegistrationDTO dto) {
        return clientService.registerClient(dto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@PathVariable Long id,
                              @RequestBody ClientUpdateDTO dto) {
        clientService.updateClient(id, dto);
    }
}
