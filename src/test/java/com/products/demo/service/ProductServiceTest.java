package com.products.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.products.demo.exception.SimilarProductsNotFoundException;
import com.products.demo.model.ProductModel;
import com.products.demo.repository.ProductRepository;

class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductService productService;
	
	private List<String> tSimilarProducts;
	private List<ProductModel> tProducts;
	private String tProductId;
	private ProductModel tProduct1;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		tSimilarProducts = new ArrayList<String>();
		tSimilarProducts.add("1");		
		
		tProducts = new ArrayList<ProductModel>();
		tProduct1 = new ProductModel("0", "product 1", 12.35, true);
		
		tProducts.add(tProduct1);
		
		tProductId = "1";
	}

	@Test
	void getSimilarProductsTest() throws Exception  {
		// prepare
		when(productRepository.getSimilarProducts(tProductId)).thenReturn(tSimilarProducts);
		when(productRepository.GetProductDetails(tProductId)).thenReturn(tProduct1);
		
		// execute
		List<ProductModel> result = productService.getSimilarProducts(tProductId);
		
		// assert
		assertNotNull(result);		
		verify(productRepository).getSimilarProducts(tProductId);
		verify(productRepository).GetProductDetails(tProductId);
		assertThat(result).usingRecursiveComparison().isEqualTo(tProducts);
	}
	
	@Test
	void getProductDetails() throws Exception {
		// prepare
		when(productRepository.GetProductDetails(tProductId)).thenReturn(tProduct1);
		
		// execute
		ProductModel result = productService.GetProductDetails(tProductId);
		
		// assert
		assertNotNull(result);
		verify(productRepository).GetProductDetails(tProductId);
		assertThat(result).usingRecursiveComparison().isEqualTo(tProduct1);
	}
	
	@Test
	void getSimilarProductsThrowsNotFoundException() throws Exception {
		// prepare
		when(productRepository.getSimilarProducts(tProductId)).thenReturn(null);
		
		// execute
		Exception exception = assertThrows(SimilarProductsNotFoundException.class, () -> productService.getSimilarProducts(tProductId));
	    String expectedMessage = "Similar products not found for product id: " + tProductId;
	    String actualMessage = exception.getMessage();

		// assert
		verify(productRepository).getSimilarProducts(tProductId);
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void getProductDetailsFailure() throws RuntimeException {
		// prepare
		when(productRepository.GetProductDetails(tProductId)).thenThrow(new RuntimeException("Error Ocurred"));
				
		// execute
		ProductModel result = productService.GetProductDetails(tProductId);

		// assert
		verify(productRepository).GetProductDetails(tProductId);
		assertNull(result);
	}

}
