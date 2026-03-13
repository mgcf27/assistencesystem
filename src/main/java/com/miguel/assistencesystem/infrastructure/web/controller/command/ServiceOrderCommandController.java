package com.miguel.assistencesystem.infrastructure.web.controller.command;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.assistencesystem.application.command.ServiceOrderService;
import com.miguel.assistencesystem.application.dto.command.ServiceOrderCreateDTO;
import com.miguel.assistencesystem.application.dto.response.ServiceOrderResponseDTO;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderCommandController {
	
	private final ServiceOrderService serviceOrderService;
	
	public ServiceOrderCommandController(ServiceOrderService serviceOrderService) {
		this.serviceOrderService = serviceOrderService;
	}

	// ===== Open =====

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceOrderResponseDTO open(@RequestBody ServiceOrderCreateDTO dto) {
        return serviceOrderService.openSO(dto);
    }

    // ===== State transitions =====

    @PatchMapping("/{id}/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void startProgress(@PathVariable Long id) {
        serviceOrderService.start(id);
    }

    @PatchMapping("/{id}/wait-for-parts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void waitingParts(@PathVariable Long id) {
        serviceOrderService.waitingParts(id);
    }

    @PatchMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finish(@PathVariable Long id) {
        serviceOrderService.finish(id);
    }

    @PatchMapping("/{id}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void close(@PathVariable Long id) {
        serviceOrderService.close(id);
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {
        serviceOrderService.cancel(id);
    }
}