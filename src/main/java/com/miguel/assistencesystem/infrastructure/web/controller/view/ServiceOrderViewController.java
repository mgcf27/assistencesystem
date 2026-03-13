package com.miguel.assistencesystem.infrastructure.web.controller.view;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.assistencesystem.application.dto.summary.SoSummaryDTO;
import com.miguel.assistencesystem.application.dto.view.ServiceOrderViewDTO;
import com.miguel.assistencesystem.application.query.search.ServiceOrderSearchService;
import com.miguel.assistencesystem.application.query.view.ServiceOrderViewService;
import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderViewController {

    private final ServiceOrderViewService serviceOrderViewService;
    private final ServiceOrderSearchService serviceOrderSearchService;

    public ServiceOrderViewController(ServiceOrderViewService serviceOrderViewService,
                                  ServiceOrderSearchService serviceOrderSearchService) {
        this.serviceOrderViewService = serviceOrderViewService;
        this.serviceOrderSearchService = serviceOrderSearchService;
    }

    // ===== View (single service order) =====

    @GetMapping("/{id}")
    public ServiceOrderViewDTO showServiceOrderById(@PathVariable Long id) {
        return serviceOrderViewService.showServiceOrderById(id);
    }

    // ===== Search (navigation) =====
    
    @GetMapping("/search/by-protocol")
    public SoSummaryDTO searchByProtocol(@RequestParam String protocol) {
        return serviceOrderSearchService.searchByProtocol(protocol);
    }

    @GetMapping("/search/by-date-range")
    public List<SoSummaryDTO> searchByDateRange(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

        return serviceOrderSearchService.searchByDateRange(from, to, page, pageSize);
    }

    @GetMapping("/search/by-status")
    public List<SoSummaryDTO> searchByStatus(
            @RequestParam ServiceOrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

        return serviceOrderSearchService.searchByStatus(status, page, pageSize);
    }

    @GetMapping("/search/by-product-serial")
    public List<SoSummaryDTO> searchByProductSerialNumber(
            @RequestParam String serialNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {

        return serviceOrderSearchService.searchByProductSerialNumber(serialNumber, page, pageSize);
    }
}
