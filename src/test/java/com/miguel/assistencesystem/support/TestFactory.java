package com.miguel.assistencesystem.support;

import com.miguel.assistencesystem.application.dto.command.ClientRegistrationDTO;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.domain.model.ServiceOrder;
import com.miguel.assistencesystem.domain.security.EmployeeRole;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

public final class TestFactory {

    private TestFactory() {}

    public static Client client() {
        return Client.register(
            "Test Client",
            randomCpf(),
            randomPhone(),
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
            "SN-123" + System.nanoTime(),
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
    
    
    public static String randomCpf() {
        int[] digits = new int[11];

        for (int i = 0; i < 9; i++) {
            digits[i] = (int) (Math.random() * 10);
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int firstCheck = 11 - (sum % 11);
        digits[9] = (firstCheck >= 10) ? 0 : firstCheck;

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digits[i] * (11 - i);
        }
        int secondCheck = 11 - (sum % 11);
        digits[10] = (secondCheck >= 10) ? 0 : secondCheck;
        return String.format("%d%d%d.%d%d%d.%d%d%d-%d%d",
                digits[0], digits[1], digits[2],
                digits[3], digits[4], digits[5],
                digits[6], digits[7], digits[8],
                digits[9], digits[10]);
    }
    
    public static String randomPhone() {
        String unique = String.valueOf(System.nanoTime());
        String last8 = unique.substring(unique.length() - 8);

        return "(11) 9" + last8; // e.g. (11) 912345678
    }
    
    public static AuthenticatedIdentity randomAuthIdentity() {
        long unique = System.nanoTime();

        return new AuthenticatedIdentity(
            unique, // id
            "user-" + unique + "@test.com", 
            EmployeeRole.ADMIN, 
            "token-" + unique 
        );
    }
}
