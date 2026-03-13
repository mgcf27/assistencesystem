package com.miguel.assistencesystem.application.query.view;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguel.assistencesystem.application.dto.view.ClientReferenceDTO;
import com.miguel.assistencesystem.application.dto.view.ProductViewDTO;
import com.miguel.assistencesystem.domain.exceptions.product.ProductNotFoundException;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.domain.model.ServiceOrder;
import com.miguel.assistencesystem.domain.valueobjects.PageRequest;
import com.miguel.assistencesystem.infrastructure.persistence.ProductJpaDAO;
import com.miguel.assistencesystem.infrastructure.persistence.ServiceOrderJpaDAO;

@Service	
@Transactional(readOnly = true)
public class ProductViewService {
	
    private final ProductJpaDAO productDAO;
    private final ServiceOrderJpaDAO serviceOrderDAO;
    
    public ProductViewService(ProductJpaDAO productDAO, ServiceOrderJpaDAO serviceOrderDAO) {
    	this.productDAO = productDAO;
    	this.serviceOrderDAO = serviceOrderDAO;
    }

    public ProductViewDTO showProductById(Long id, int page, int pageSize) {
        
            Product product = productDAO.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            ClientReferenceDTO owner =
                    productDAO.findOwnerReferenceByProductId(id);
            
            PageRequest pr = new PageRequest(page, pageSize);
            
            List<ServiceOrder> serviceOrders =
                serviceOrderDAO.findByProductId(id, pr);

            return ProductViewDTO.fromEntity(product, owner, serviceOrders);
    }
}