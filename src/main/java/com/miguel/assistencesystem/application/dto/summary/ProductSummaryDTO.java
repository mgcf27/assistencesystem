package com.miguel.assistencesystem.application.dto.summary;

import com.miguel.assistencesystem.domain.model.Product;

public class ProductSummaryDTO {
    private final Long id;
    private final String model;
    private final String voltage;
    //private boolean hasOpenOrder; maybe this might be some useful info when visualizing a prod
    
    public ProductSummaryDTO(Long id, String model, String voltage) {
    	this.id = id;
    	this.model = model;
    	this.voltage = voltage;
    }
    
    
    public Long getId() { return id; }
    public String getModel() { return model; }
    public String getVoltage() { return voltage; }
    //public boolean isHasOpenOrder() { return hasOpenOrder; }
    
    public static ProductSummaryDTO fromEntity(Product product) {
        return new ProductSummaryDTO(
        		product.getId(),
        		product.getModel(),
        		product.getVoltage()
        		);
    }
}
