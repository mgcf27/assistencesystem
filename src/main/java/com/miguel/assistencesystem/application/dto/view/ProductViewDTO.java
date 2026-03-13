package com.miguel.assistencesystem.application.dto.view;

import java.util.List;

import com.miguel.assistencesystem.application.dto.summary.SoSummaryDTO;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.domain.model.ServiceOrder;

public class ProductViewDTO {
	private final Long id;
	private final String model;
	private final String commercialModel;
	private final String manufacturerCode;
	private final String serialNumber;
	private final String voltage;
	private final ClientReferenceDTO owner;
	private final List<SoSummaryDTO> serviceOrders;

	public ProductViewDTO(
			Long id,
			String model,
			String commercialModel,
			String manufacturerCode,
			String serialNumber,
			String voltage,
			ClientReferenceDTO owner,
			List<SoSummaryDTO> serviceOrders
	) {
		this.id = id;
		this.model = model;
		this.commercialModel = commercialModel;
		this.manufacturerCode = manufacturerCode;
		this.serialNumber = serialNumber;
		this.voltage = voltage;
		this.owner = owner;
		this.serviceOrders = serviceOrders;
	}

	public Long getId() {return id;}
	public String getModel() { return model; }
	public String getCommercialModel() { return commercialModel; }
	public String getManufacturerCode() { return manufacturerCode; }
	public String getSerialNumber() { return serialNumber; }
	public String getVoltage() { return voltage; }
	public ClientReferenceDTO getOwner() { return owner; }	
	public List<SoSummaryDTO> getSoSummary() { return serviceOrders; }
	
	public static ProductViewDTO fromEntity(Product product,ClientReferenceDTO owner, List<ServiceOrder> serviceOrders) {
		return new ProductViewDTO(
				product.getId(),
				product.getModel(),
				product.getCommercialModel(),
				product.getManufacturerCode(),
				product.getSerialNumber(),
				product.getVoltage(),
				owner,
				serviceOrders.stream().map(SoSummaryDTO::fromEntity).toList()
				);
	}
}
