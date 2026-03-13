package com.miguel.assistencesystem.domain.exceptions.serviceorder;

import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;
import com.miguel.assistencesystem.domain.exceptions.InvalidDomainStateException;


@SuppressWarnings("serial")
public class InvalidServiceOrderStatusException extends InvalidDomainStateException {
    
    public InvalidServiceOrderStatusException(ServiceOrderStatus currentStatus) {
        super("Cannot modify service order with status: " + currentStatus);
    }
    
    public InvalidServiceOrderStatusException(ServiceOrderStatus from, ServiceOrderStatus to) {
        super(String.format("Invalid status transition from %s to %s", from, to));
    }
}
