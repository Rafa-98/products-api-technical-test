package com.products.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.products.demo.exception.SimilarProductsNotFoundException;
import com.products.demo.model.ProductModel;
import com.products.demo.service.ProductService;

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/product")
@Validated
@Slf4j
public class ProductController {
	
	private ProductService service = new ProductService();
	
	@GetMapping("/{productId}/similar")
	public ResponseEntity<List<ProductModel>> getSimilarProducts(@PathVariable @NotBlank @Pattern(regexp = "/^[0-9]+$/") String productId) throws SimilarProductsNotFoundException {
		log.info("Request Received - GET:/product/" + productId + "/similar");
		return ResponseEntity.ok(service.getSimilarProducts(productId));
	}

}
