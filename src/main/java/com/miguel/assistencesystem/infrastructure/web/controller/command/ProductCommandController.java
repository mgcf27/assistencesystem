package com.miguel.assistencesystem.infrastructure.web.controller.command;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.assistencesystem.application.command.ProductService;
import com.miguel.assistencesystem.application.dto.command.ProductCreateDTO;
import com.miguel.assistencesystem.application.dto.response.ProductResponseDTO;

@RestController
@RequestMapping("/products")
public class ProductCommandController {
	
	private final ProductService productService;
	
	public ProductCommandController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/identified")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO installIdentified(
            @RequestBody ProductCreateDTO dto) {
        return productService.installProductIdentified(dto);
    }

    @PostMapping("/unidentified")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO installUnidentified(
            @RequestBody ProductCreateDTO dto) {
        return productService.installProductUnidentified(dto);
    }

    @PatchMapping("/{id}/identify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void identifyProduct(@PathVariable Long id,
                                @RequestBody ProductCreateDTO dto) {
        productService.identifyProduct(id, dto);
    }

}
