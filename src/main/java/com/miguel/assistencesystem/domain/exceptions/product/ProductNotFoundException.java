package com.miguel.assistencesystem.domain.exceptions.product;

import com.miguel.assistencesystem.domain.exceptions.NotFoundException;

@SuppressWarnings("serial")
public class ProductNotFoundException extends NotFoundException {
    
    public ProductNotFoundException(String message) {
        super(message);
    }
}
