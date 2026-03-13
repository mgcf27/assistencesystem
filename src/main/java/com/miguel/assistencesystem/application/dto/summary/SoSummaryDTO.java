package com.miguel.assistencesystem.application.dto.summary;

import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.domain.model.ServiceOrder;
import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;

public class SoSummaryDTO {
	private final Long id;
	private final String protocolNumber;
	private final Product product;
	private final ServiceOrderStatus status;
	
	public SoSummaryDTO(Long id, String protocolNumber, Product product, ServiceOrderStatus status) {
		this.id = id;
		this.protocolNumber = protocolNumber;
		this.product = product;
		this.status = status;
	}
	
	public Long getId() {return id;}
	public String getProtocolNumber() { return protocolNumber; }
	public Product getProduct() {return product;}
	public ServiceOrderStatus getStatus() {return status;}
	
	
	public static SoSummaryDTO fromEntity(ServiceOrder so) {
		return new SoSummaryDTO(
				so.getId(),
				so.getProtocolNumber(),
				so.getProduct(),
				so.getStatus()
				);
	}
}
