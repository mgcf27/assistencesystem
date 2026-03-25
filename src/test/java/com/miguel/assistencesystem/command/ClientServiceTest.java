package com.miguel.assistencesystem.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.command.ClientService;
import com.miguel.assistencesystem.application.dto.command.ClientRegistrationDTO;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;
import com.miguel.assistencesystem.domain.exceptions.client.InvalidClientDataException;
import com.miguel.assistencesystem.domain.security.EmployeeRole;
import com.miguel.assistencesystem.infrastructure.persistence.ClientJpaDAO;
import com.miguel.assistencesystem.infrastructure.security.context.AuthenticationContext;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;
import com.miguel.assistencesystem.support.TestFactory;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ClientServiceTest {
	
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientJpaDAO clientDAO;
	@Autowired
	private EntityManager entityManager;
	
	private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
	
	@BeforeEach
	void setupAuth() {
	    AuthenticationContext.set(
	        new AuthenticatedIdentity(
	        		1L,
	        		"test@system.com",
	        		EmployeeRole.ADMIN,
	        		"test-token")
	    );
	}

	@AfterEach
	void clearAuth() {
	    AuthenticationContext.clear();
	}
	
	// Observation: The same logic below can be applied to updating
	
	@Test
	void shouldNotRegisterIfValidationFail() {
		//Change the parameters in clientForFailing... to check fields validation
		ClientRegistrationDTO client = TestFactory.clientDto();
		
		assertThrows(InvalidClientDataException.class,() ->{
			clientService.registerClient(client);
		});
		
		flushAndClear();
		
		boolean exists = clientDAO.existsByCpf(client.getCpf());
	    assertFalse(exists, "Client should not be saved when validation fails");
	}

	
	@Test
	void shouldNotAllowDuplicatedCpfInDb() {
		
		ClientRegistrationDTO client = new ClientRegistrationDTO(
				 "Test Client",
		         "123.456.789-09",
		         "(11) 99999-8888",
		         "Test Address",
		         "test@email.com"
				);
	    clientService.registerClient(client); // First registration
	    
	    flushAndClear();

	    ClientRegistrationDTO duplicateCpfClient = new ClientRegistrationDTO(
	    		 "Another name",
	             "123.456.789-09", //same cpf
	             "(11) 99999-5555",
	             "Test Address 2",
	             "test2@email.com"
	    );
	    

	    assertThrows(ConflictException.class, () -> {
	        clientService.registerClient(duplicateCpfClient);
	    });
	    
	    flushAndClear();
	    
	    assertTrue(clientDAO.existsByCpf(client.getCpf()), "Original client should still exist");
	}
}
