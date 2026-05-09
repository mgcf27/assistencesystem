package com.miguel.assistencesystem.application.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ServiceOrderCreateDTO {
	@NotNull
    private Long productId;
	@NotNull
	@NotBlank
	@Size(max=300)
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

