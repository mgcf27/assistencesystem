package com.miguel.assistencesystem.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.command.ServiceOrderService;
import com.miguel.assistencesystem.application.dto.command.ServiceOrderCreateDTO;
import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.InvalidServiceOrderDataException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.InvalidServiceOrderStatusException;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.ServiceOrderAlreadyOpenException;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.domain.model.ServiceOrder;
import com.miguel.assistencesystem.domain.security.EmployeeRole;
import com.miguel.assistencesystem.infrastructure.persistence.ServiceOrderJpaDAO;
import com.miguel.assistencesystem.infrastructure.security.context.AuthenticationContext;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;
import com.miguel.assistencesystem.support.TestFactory;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ServiceOrderServiceTest {
	
	@Autowired
    private ServiceOrderService serviceOrderService;
    @Autowired
    private ServiceOrderJpaDAO serviceOrderDAO;
    @Autowired
    private EntityManager entityManager;
    
    private void flushAndClear() {
        entityManager.flush();  // Force sync with DB
        entityManager.clear();  // Clear Hibernate cache
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
	
	
    
    //You can only have one service order in progress for product
	@Test
	void shouldNotOpenSecondServiceOrderForSameProduct() {
	    // arrange
	    Client client = TestFactory.client();
	    Product product = TestFactory.identifiedProduct(client);

	    entityManager.persist(client);
	    entityManager.persist(product);
	    
	    flushAndClear();

	    serviceOrderService.openSO(
	        new ServiceOrderCreateDTO(product.getId(), "Problem A")
	    );
	    
	    flushAndClear();

	    // act + assert
	    assertThrows(ServiceOrderAlreadyOpenException.class, () -> {
	        serviceOrderService.openSO(
	            new ServiceOrderCreateDTO(product.getId(), "Problem B")
	        );
	    });

	    // verify DB state
	    long count = serviceOrderDAO.countByProduct(product.getId());
	    assertEquals(1, count);
	}
	
	@Test
	void shouldNotOpenWithoutProblemDescription() { 
		Client client = TestFactory.client();
	    Product product = TestFactory.identifiedProduct(client);

	    entityManager.persist(client);
	    entityManager.persist(product);
	    
	    flushAndClear();
	    
	    //Test not null or empty string
	    assertThrows(InvalidServiceOrderDataException.class, () -> {
	    	serviceOrderService.openSO(
	    	        new ServiceOrderCreateDTO(product.getId(), "")
	    	    );
	    });
	    
	    flushAndClear();
	    
	    long count = serviceOrderDAO.countByProduct(product.getId());
	    assertEquals(0, count);
		
	}
	
	//You might want to test other transitions changing the function called and the status
	
	@Test
	void shouldNotAllowInvalidTransition() {
		Client client = TestFactory.client();
		Product product = TestFactory.identifiedProduct(client);

	    entityManager.persist(client);
	    entityManager.persist(product);
	    
	    flushAndClear();

	    ServiceOrder so = TestFactory.serviceOrder(client, product, "Problem description");
	    
	    entityManager.persist(so);
	    
	    flushAndClear();
		
	    assertThrows(InvalidServiceOrderStatusException.class, () -> {
	        serviceOrderService.finish(so.getId());
	    });
	    
	    ServiceOrder persisted =
	            serviceOrderDAO.findById(so.getId()).orElseThrow();

	    assertEquals(ServiceOrderStatus.OPEN, persisted.getStatus());
	  
	}

}
