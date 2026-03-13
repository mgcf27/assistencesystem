package com.miguel.assistencesystem.application.dto.view;

import java.util.List;

import com.miguel.assistencesystem.application.dto.summary.ProductSummaryDTO;
import com.miguel.assistencesystem.application.dto.summary.SoSummaryDTO;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.domain.model.ServiceOrder;

public class ClientViewDTO {
    private final Long id;
    private final String name;
    private final String cpf;
    private final String phone;
    private final String address;
    private final List<ProductSummaryDTO> products;
    private final List<SoSummaryDTO> serviceOrders;

    public ClientViewDTO(
    		Long id,
    		String name,
    		String cpf,
    		String phone,
    		String address,
    		List<ProductSummaryDTO> products,
    		List<SoSummaryDTO> serviceOrders
    ) {
        
    	this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.products = products;
        this.serviceOrders = serviceOrders;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCpf() { return cpf; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public List<ProductSummaryDTO> getProductSummary() { return products;}
    public List<SoSummaryDTO> getSoSummary() { return serviceOrders; }
    
    
    public static ClientViewDTO fromEntity(
    		Client client,
    		List<Product> products,
    		List<ServiceOrder> serviceOrders) {
        return new ClientViewDTO(
            client.getId(),
            client.getName(),
            client.getCpf(),
            client.getPhone(),
            client.getAddress(),
            products.stream().map(ProductSummaryDTO::fromEntity).toList(),
            serviceOrders.stream().map(SoSummaryDTO::fromEntity).toList()
        );
    }
}
