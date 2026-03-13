package com.miguel.assistencesystem.application.dto.command;

public class ProductCreateDTO {
    private Long clientId;              
    private String model;               
    private String commercialModel;     
    private String manufacturerCode;    
    private String serialNumber;        
    private String voltage;             
    
   
    public Long getClientId() { return clientId; }
    public String getModel() { return model; }
    public String getCommercialModel() { return commercialModel; }
    public String getManufacturerCode() { return manufacturerCode; }
    public String getSerialNumber() { return serialNumber; }
    public String getVoltage() { return voltage; }
    
    
    public void setClientId(Long clientId) { this.clientId = clientId; }
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
    
    public ProductCreateDTO() {}
    
    public ProductCreateDTO(Long clientId,
    		String model,
    		String commercialModel,
    		String manufacturerCode,    
    		String serialNumber,        
    		String voltage     
    		) {
    	this.clientId = clientId;
    	this.model = model;
    	this.commercialModel = commercialModel;
    	this.manufacturerCode = manufacturerCode;
    	this.serialNumber = serialNumber;
    	this.voltage = voltage;
    }
}
