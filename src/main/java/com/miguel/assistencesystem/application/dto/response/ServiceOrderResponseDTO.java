package com.miguel.assistencesystem.application.dto.response;

import java.time.LocalDateTime;

import com.miguel.assistencesystem.domain.model.ServiceOrder;
import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;

public record ServiceOrderResponseDTO(
	    Long id,
	    String protocolNumber,
	    ServiceOrderStatus status,
	    LocalDateTime openedAt,
	    LocalDateTime closedAt
	) {
	    public static ServiceOrderResponseDTO fromEntity(ServiceOrder so) {
	        return new ServiceOrderResponseDTO(
	            so.getId(),
	            so.getProtocolNumber(),
	            so.getStatus(),
	            so.getOpenedAt(),
	            so.getClosedAt()
	        );
	    }
	}
