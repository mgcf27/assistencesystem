package com.miguel.assistencesystem.application.dto.view;

import java.time.LocalDateTime;

import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;
import com.miguel.assistencesystem.domain.model.ServiceOrder;

public class ServiceOrderViewDTO {
	private final Long id;
	private final String protocolNumber;
	private final ClientForSoViewDTO client;
	private final ProductForSoViewDTO product;
	private final String problemDescription;
	private final LocalDateTime openedAt;
	private final LocalDateTime closedAt;
	private final ServiceOrderStatus status;
	
	public ServiceOrderViewDTO(
			Long id,
			String protocolNumber,
			ClientForSoViewDTO client,
			ProductForSoViewDTO product,
			String problemDescription,
			LocalDateTime openedAt,
			LocalDateTime closedAt,
			ServiceOrderStatus status
	) {
		this.id = id;
		this.protocolNumber = protocolNumber;
		this.client = client;
		this.product = product;
		this.problemDescription = problemDescription;
		this.openedAt = openedAt;
		this.closedAt = closedAt;
		this.status = status;
	}
	
	public Long getId() { return id; }
	public String getProtocolNumber() { return protocolNumber; }
	public ClientForSoViewDTO getClient() { return client; }
	public ProductForSoViewDTO getProduct() { return product; }
	public String getProblemDescription() { return problemDescription; }
	public LocalDateTime getOpenedAt() { return openedAt; }
	public LocalDateTime getClosedAt() { return closedAt; }
	public ServiceOrderStatus getStatus() { return status; }
	
	public static ServiceOrderViewDTO fromEntity(ServiceOrder serviceOrder) {
		return new ServiceOrderViewDTO(
				serviceOrder.getId(),
				serviceOrder.getProtocolNumber(),
				ClientForSoViewDTO.fromEntity(serviceOrder.getClient()),
				ProductForSoViewDTO.fromEntity(serviceOrder.getProduct()),
				serviceOrder.getProblemDescription(),
				serviceOrder.getOpenedAt(),
				serviceOrder.getClosedAt(),
				serviceOrder.getStatus()
		);
	}
}
