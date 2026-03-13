package com.miguel.assistencesystem.support;

import com.miguel.assistencesystem.application.dto.command.ClientRegistrationDTO;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.domain.model.ServiceOrder;

public final class TestFactory {

    private TestFactory() {}

    public static Client client() {
        return Client.register(
            "Test Client",
            "123.456.789-09",
            "(11) 99999-8888",
            "Test Address",
            "test@email.com"
        );
    }
    
    public static ClientRegistrationDTO clientDto() {
    	return  new ClientRegistrationDTO(
    			 "Test Client",
    	         "111.111.111.11",//no valid cpf
    	         "(11) 99999-8888",
    	         "Test Address",
    	         "test@email.com"
    			);	
    }

    public static Product identifiedProduct(Client client) {
        return Product.registerIdentified(
            client,
            "BWK11",
            "Brastemp BWK11",
            "BRST-001",
            "SN-123",
            "220V"
        );
    }

    public static Product unidentifiedProduct(Client client) {
        return Product.registerUnidentified(
            client,
            "Washing Machine"
        );
    }
    
    public static ServiceOrder serviceOrder(Client client, Product product, String problemDescription) {
    	return ServiceOrder.open(client, product, problemDescription);
    }
}
