package com.miguel.assistencesystem.infrastructure.web.controller.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.assistencesystem.application.dto.view.ProductViewDTO;
import com.miguel.assistencesystem.application.query.view.ProductViewService;

@RestController
@RequestMapping("/products")
public class ProductViewController {
	
	private final ProductViewService productViewService;

	public ProductViewController(ProductViewService productViewService) {
		this.productViewService = productViewService;
	}
	
	
	// ===== View (single product) =====
	
	@GetMapping("/{id}")
	public ProductViewDTO showProduct(
			@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
		
		return productViewService.showProductById(id, page, pageSize);
	}

	
	

}
