package com.miguel.assistencesystem.application.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductIdentifyDTO {
	@NotNull
	@NotBlank
	@Size(max=100)
    private String model;
	@NotNull
	@NotBlank
	@Size(max=100)
    private String commercialModel;
	@NotNull
	@NotBlank
	@Size(max=50)
    private String manufacturerCode; 
	@NotNull
	@NotBlank
	@Size(max=50)
    private String serialNumber;
	@NotNull
	@NotBlank
	@Size(max=20)
    private String voltage;             
    
 
    public String getModel() { return model; }
    public String getCommercialModel() { return commercialModel; }
    public String getManufacturerCode() { return manufacturerCode; }
    public String getSerialNumber() { return serialNumber; }
    public String getVoltage() { return voltage; }
    
    
    public void setModel(String model) { this.model = model; }
    public void setCommercialModel(String commercialModel) { 
        this.commercialModel = commercialModel; 
    }
    public void setManufacturerCode(String manufacturerCode) { 
        this.manufacturerCode = manufacturerCode; 
    }
    public void setSerialNumber(String serialNumber) { 
        this.serialNumber = serialNumber; 
    }
    public void setVoltage(String voltage) { this.voltage = voltage; }
    
    public boolean hasUpdates() {
        return model != null || commercialModel != null || 
               manufacturerCode != null || serialNumber != null || 
               voltage != null;
    }
}
