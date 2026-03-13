package com.miguel.assistencesystem.application.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.dto.command.ProductCreateDTO;
import com.miguel.assistencesystem.application.dto.response.ProductResponseDTO;
import com.miguel.assistencesystem.application.security.AuthenticationFacade;
import com.miguel.assistencesystem.domain.exceptions.ConflictException;
import com.miguel.assistencesystem.domain.exceptions.client.ClientNotFoundException;
import com.miguel.assistencesystem.domain.exceptions.product.InvalidProductDataException;
import com.miguel.assistencesystem.domain.exceptions.product.InvalidProductStateException;
import com.miguel.assistencesystem.domain.exceptions.product.ProductNotFoundException;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.infrastructure.persistence.ClientJpaDAO;
import com.miguel.assistencesystem.infrastructure.persistence.ProductJpaDAO;
import com.miguel.assistencesystem.infrastructure.security.identity.AuthenticatedIdentity;

@Service
@Transactional
public class ProductService {
	
    private final ProductJpaDAO productDAO;
    private final ClientJpaDAO clientDAO;
    private final AuthenticationFacade authentication;
    
    public ProductService(
    		ProductJpaDAO productDAO,
    		ClientJpaDAO clientDAO,
    		AuthenticationFacade authentication) {
    	this.clientDAO = clientDAO;
    	this.productDAO = productDAO;
    	this.authentication = authentication;
    }

	public ProductResponseDTO installProductIdentified(ProductCreateDTO dto) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		Client client = clientDAO.findById(dto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(dto.getClientId()));
        
        if (productDAO.existsBySerialNumber(dto.getSerialNumber())) {
            throw new ConflictException("Product already exists. Wanna see it instead?");
        }
        
        Product product = Product.registerIdentified(
                client,
                dto.getModel(),
                dto.getCommercialModel(),
                dto.getManufacturerCode(),
                dto.getSerialNumber(),
                dto.getVoltage()
        );
        
        productDAO.persist(product);
        
        return ProductResponseDTO.fromEntity(product);
	}
		
	public ProductResponseDTO installProductUnidentified(ProductCreateDTO dto) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();
		
		if (dto.getModel() == null | dto.getModel().isBlank()) {
			throw new InvalidProductDataException("Product model cannot be empty");
		}
		
		Client client = clientDAO.findById(dto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(dto.getClientId()));

        Product product = Product.registerUnidentified(client, dto.getModel());
        
        productDAO.persist(product);
        
        return ProductResponseDTO.fromEntity(product);
	    
	}
	
	public ProductResponseDTO identifyProduct(Long productId, ProductCreateDTO dto) {
		AuthenticatedIdentity identity = authentication.requireAuthenticated();

		if (productDAO.existsBySerialNumber(dto.getSerialNumber())) {
            throw new ConflictException("Serial number already registered");
        }

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        
        if(product.isIdentified()) {
        	throw new InvalidProductStateException("Product is already identified");
        }

        product.identify(
                dto.getModel(),
                dto.getCommercialModel(),
                dto.getManufacturerCode(),
                dto.getSerialNumber(),
                dto.getVoltage()
        );
        
        return ProductResponseDTO.fromEntity(product);
	}		
}
