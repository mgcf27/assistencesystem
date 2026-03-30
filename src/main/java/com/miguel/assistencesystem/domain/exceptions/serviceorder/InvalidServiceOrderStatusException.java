package com.miguel.assistencesystem.domain.exceptions.serviceorder;

import com.miguel.assistencesystem.domain.enums.DomainErrorCode;
import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;
import com.miguel.assistencesystem.domain.exceptions.InvalidDomainStateException;


@SuppressWarnings("serial")
public class InvalidServiceOrderStatusException extends InvalidDomainStateException {
    
    public InvalidServiceOrderStatusException(ServiceOrderStatus currentStatus) {
        super(
        		DomainErrorCode.INVALID_SERVICE_ORDER_STATUS,
        		"Cannot modify service order with status: " + currentStatus);
    }
    
    public InvalidServiceOrderStatusException(ServiceOrderStatus from, ServiceOrderStatus to) {
        super(
        		DomainErrorCode.INVALID_SERVICE_ORDER_STATUS,
        		String.format("Invalid status transition from %s to %s", from, to));
    }
}
