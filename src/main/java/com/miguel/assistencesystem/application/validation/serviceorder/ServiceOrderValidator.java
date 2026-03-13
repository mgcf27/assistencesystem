package com.miguel.assistencesystem.application.validation.serviceorder;

import java.util.ArrayList;
import java.util.List;

import com.miguel.assistencesystem.application.dto.command.ServiceOrderCreateDTO;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.InvalidServiceOrderDataException;

public class ServiceOrderValidator {
    
    public static void validateForCreation(ServiceOrderCreateDTO dto) {
        List<String> errors = new ArrayList<>();
        
        if (dto.getProductId() == null) {
            errors.add("Product ID is required");
        }
        
        if (dto.getProblemDescription() == null || dto.getProblemDescription().isBlank()) {
            errors.add("Problem description is required");
        }
        
        if (dto.getProblemDescription() != null && dto.getProblemDescription().length() > 500) {
            errors.add("Problem description cannot exceed 500 characters");
        }
        
        if (!errors.isEmpty()) {
            throw new InvalidServiceOrderDataException(errors);
        }
    }
}
