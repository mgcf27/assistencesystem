package com.miguel.assistencesystem.application.dto.response;

import com.miguel.assistencesystem.domain.model.Product;

public record ProductResponseDTO(
	    Long id,
	    Long clientId,
	    String model,
	    String serialNumber,
	    String voltage
	) {
	    public static ProductResponseDTO fromEntity(Product product) {
	        return new ProductResponseDTO(
	            product.getId(),
	            product.getClient().getId(),
	            product.getModel(),
	            product.getSerialNumber(),
	            product.getVoltage()
	        );
	    }
	}