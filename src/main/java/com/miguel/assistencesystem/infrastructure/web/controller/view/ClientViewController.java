package com.miguel.assistencesystem.infrastructure.web.controller.view;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.assistencesystem.application.dto.summary.ClientSummaryDTO;
import com.miguel.assistencesystem.application.dto.view.ClientViewDTO;
import com.miguel.assistencesystem.application.query.search.ClientSearchService;
import com.miguel.assistencesystem.application.query.view.ClientViewService;

@RestController
@RequestMapping("/clients")
public class ClientViewController {

    private final ClientViewService clientViewService;
    private final ClientSearchService clientSearchService;

    public ClientViewController(ClientViewService clientViewService,
                            ClientSearchService clientSearchService) {
        this.clientViewService = clientViewService;
        this.clientSearchService = clientSearchService;
    }

    // ===== View (single client) =====

    @GetMapping("/{id}")
    public ClientViewDTO showClientById(
        @PathVariable Long id,
        @RequestParam(defaultValue = "0") int prodPage,
        @RequestParam(defaultValue = "10") int prodPageSize,
        @RequestParam(defaultValue = "0") int soPage,
        @RequestParam(defaultValue = "10") int soPageSize) {
        
        return clientViewService.showClientById(id, prodPage, prodPageSize, soPage, soPageSize);
    }

    
    @GetMapping("/by-cpf/{cpf}")
    public ClientViewDTO showClientByCpf(
    		@PathVariable String cpf,
    		@RequestParam(defaultValue = "0") int prodPage,
    	    @RequestParam(defaultValue = "10") int prodPageSize,
    	    @RequestParam(defaultValue = "0") int soPage,
    	    @RequestParam(defaultValue = "10") int soPageSize) {
        return clientViewService.showClientByCpf(cpf, prodPage, prodPageSize, soPage, soPageSize);
    }

    @GetMapping("/by-phone/{phone}")
    public ClientViewDTO showClientByPhone(
    		@PathVariable String phone,
    		@RequestParam(defaultValue = "0") int prodPage,
    	    @RequestParam(defaultValue = "10") int prodPageSize,
    	    @RequestParam(defaultValue = "0") int soPage,
    	    @RequestParam(defaultValue = "10") int soPageSize) {
        return clientViewService.showClientByPhone(phone, prodPage, prodPageSize, soPage, soPageSize);
    }

    // ===== Search (navigation) =====

    @GetMapping("/search")
    public List<ClientSummaryDTO> searchClientByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

        return clientSearchService.searchByName(name, page, pageSize);
    }

    @GetMapping("/search/by-cpf")
    public ClientSummaryDTO searchClientByCpf(@RequestParam String cpf) {
        return clientSearchService.searchByCpf(cpf);
    }

    @GetMapping("/search/by-phone")
    public ClientSummaryDTO searchClientByPhone(@RequestParam String phone) {
        return clientSearchService.searchByPhone(phone);
    }
}
