package com.miguel.assistencesystem.application.dto.command;

public class ProductIdentifyDTO {
    private String model;               
    private String commercialModel;     
    private String manufacturerCode;    
    private String serialNumber;        
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
