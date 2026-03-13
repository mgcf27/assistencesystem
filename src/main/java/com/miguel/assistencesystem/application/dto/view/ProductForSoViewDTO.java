package com.miguel.assistencesystem.application.dto.view;

import com.miguel.assistencesystem.domain.model.Product;

public class ProductForSoViewDTO {
    private final Long id;
    private final String model;
    private final String commercialModel;
    private final String manufacturerCode;
    private final String serialNumber;
    private final String voltage;
    
    public ProductForSoViewDTO(
            Long id,
            String model,
            String commercialModel,
            String manufacturerCode,
            String serialNumber,
            String voltage
    ) {
        this.id = id;
        this.model = model;
        this.commercialModel = commercialModel;
        this.manufacturerCode = manufacturerCode;
        this.serialNumber = serialNumber;
        this.voltage = voltage;
    }
    
    public Long getId() { return id; }
    public String getModel() { return model; }
    public String getCommercialModel() { return commercialModel; }
    public String getManufacturerCode() { return manufacturerCode; }
    public String getSerialNumber() { return serialNumber; }
    public String getVoltage() { return voltage; }
    
    public static ProductForSoViewDTO fromEntity(Product product) {
        return new ProductForSoViewDTO(
                product.getId(),
                product.getModel(),
                product.getCommercialModel(),
                product.getManufacturerCode(),
                product.getSerialNumber(),
                product.getVoltage()
        );
    }
}
