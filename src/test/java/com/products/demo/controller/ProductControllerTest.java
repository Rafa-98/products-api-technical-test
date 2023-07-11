package com.products.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.products.demo.model.ProductModel;
import com.products.demo.service.ProductService;

class ProductControllerTest {
	
	@Mock
	private ProductService productService;
	
	@InjectMocks
	private ProductController productController;
	
	private List<ProductModel> tProducts;
	private String tProductId;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		tProducts = new ArrayList<ProductModel>();
		ProductModel product1 = new ProductModel("0", "product 1", 12.35, true);
		tProducts.add(product1);
		
		tProductId = "1";
	}

	@Test
	void getSimilarProductsTest() throws Exception  {
		// prepare
		when(productService.getSimilarProducts(tProductId)).thenReturn(tProducts);
		
		// execute
		ResponseEntity<List<ProductModel>> result = productController.getSimilarProducts(tProductId);
		
		// assert		
		assertNotNull(result);
		verify(productService).getSimilarProducts(tProductId);
		assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(tProducts);
	}

}
