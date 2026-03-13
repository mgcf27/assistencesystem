package com.miguel.assistencesystem.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.command.ProductService;
import com.miguel.assistencesystem.application.dto.command.ProductCreateDTO;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;
import com.miguel.assistencesystem.domain.exceptions.product.InvalidProductDataException;
import com.miguel.assistencesystem.domain.exceptions.product.InvalidProductStateException;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.infrastructure.persistence.ClientJpaDAO;
import com.miguel.assistencesystem.infrastructure.persistence.ProductJpaDAO;
import com.miguel.assistencesystem.support.TestFactory;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ProductServiceTest {
	
	@Autowired ProductService productService;
	@Autowired ProductJpaDAO productDAO;
	@Autowired ClientJpaDAO clientDAO;
	@Autowired
    private EntityManager entityManager;
	
	private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
	
	
	@Test
	void shouldNotAllowBreakingUniquenessConstraint() {
		Client client = TestFactory.client();
		clientDAO.persist(client);
		
		flushAndClear();
		
		ProductCreateDTO product1 = new ProductCreateDTO(
				client.getId(),
				"DishWasher",
				"Brastemp BWK12",
	            "BRST-231",
	            "SN-189",
	            "127V"
				);
		
		ProductCreateDTO product2 = new ProductCreateDTO(
				client.getId(),
				"DishWasher",
				"Brastemp BWL11",
	            "BRST-999",
	            "SN-189",   //same serialNumber
	            "127V"
				);
				
		productService.installProductIdentified(product1);
		
		flushAndClear();
		
		assertThrows(ConflictException.class,() -> {
			productService.installProductIdentified(product2);
		});
		
		flushAndClear();
		
		assertTrue(productDAO.existsBySerialNumber(product1.getSerialNumber()), "Original Product Should Still Exist");
	}
	
	//Technician has to know at least if it is a refrigerator, a washing machine, and etc...
	@Test
	void shouldNotAllowUnidentifiedProductWithoutMinimalClassification() {
	    Client client = TestFactory.client();
	    clientDAO.persist(client);
	    
	    flushAndClear();

	    ProductCreateDTO product = new ProductCreateDTO(
	        client.getId(),
	        "",     // no model
	        null,
	        null,
	        null,
	        null
	    );

	    assertThrows(InvalidProductDataException.class, () -> {
	        productService.installProductUnidentified(product);
	    });
	    
	    flushAndClear();

	    assertEquals(0, clientDAO.countProductsByClientId(client.getId()));
	}
	
	@Test
	void shouldNotAllowIdentifyProductTwice() {
	    Client client = TestFactory.client();
	    clientDAO.persist(client);
	    
	    flushAndClear();

	    Product product = TestFactory.identifiedProduct(client);
	    productDAO.persist(product);
	    
	    flushAndClear();

	    ProductCreateDTO dto = new ProductCreateDTO(
	        client.getId(),
	        "Anything",
	        "Anything",
	        "Anything",
	        "Anything",
	        "230V"
	    );

	    assertThrows(InvalidProductStateException.class, () -> {
	        productService.identifyProduct(product.getId(), dto);
	    });
	    
	    flushAndClear();

	    Product refreshed = productDAO.findById(product.getId()).orElseThrow();

	    assertEquals(product.getSerialNumber(), refreshed.getSerialNumber());
	    assertEquals(product.getManufacturerCode(), refreshed.getManufacturerCode());
	    assertEquals(product.getVoltage(), refreshed.getVoltage());
	}

}
