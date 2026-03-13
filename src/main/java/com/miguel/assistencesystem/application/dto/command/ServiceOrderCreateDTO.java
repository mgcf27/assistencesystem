package com.miguel.assistencesystem.application.dto.command;

public class ServiceOrderCreateDTO {             
    private Long productId;             
    private String problemDescription; 
    
    public Long getProductId() { return productId; }
    public String getProblemDescription() { return problemDescription; }
    
    public void setProductId(Long productId) { this.productId = productId; }
    public void setProblemDescription(String problemDescription) { 
        this.problemDescription = problemDescription;    
    }
    
    public ServiceOrderCreateDTO() {}
    
    //Used when testing
    public ServiceOrderCreateDTO(Long productId, String problemDescription) {
        this.productId = productId;
        this.problemDescription = problemDescription;
    }
}

