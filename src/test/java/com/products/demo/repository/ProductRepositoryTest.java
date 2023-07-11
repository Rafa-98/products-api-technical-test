package com.products.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.products.demo.controller.ProductController;
import com.products.demo.model.ProductModel;
import com.products.demo.service.ProductService;

class ProductRepositoryTest {

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private ProductRepository productRepository;
	
	static final String url = "http://localhost:3001/product/";
	
	private String[] tSimilarProducts;
	private List<ProductModel> tProducts;
	private String tProductId;
	private ProductModel tProduct1;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		//tSimilarProducts = new ArrayList<String>();
		//tSimilarProducts.add("2");
		tSimilarProducts = new String[]{"2"};
		
		tProducts = new ArrayList<ProductModel>();
		tProduct1 = new ProductModel("0", "product 1", 12.35, true);
		tProducts.add(tProduct1);
		
		tProductId = "1";
	}

	@Test
	void getSimilarProductsTest() throws Exception  {
		when(restTemplate.getForEntity(url + tProductId + "/similarids", String[].class)).thenReturn(new ResponseEntity(tSimilarProducts, HttpStatus.OK));
		List<String> result = productRepository.getSimilarProducts(tProductId);
		assertNotNull(result);
		verify(restTemplate).getForEntity(url + tProductId + "/similarids", String[].class);
		System.out.print("The result is: " + result);
		System.out.print("Array to list is: " + Arrays.asList(tSimilarProducts));
		assertThat(result).usingRecursiveComparison().isEqualTo(Arrays.asList(tSimilarProducts));
	}
	
	@Test
	void getProductDetails() throws Exception  {
		when(restTemplate.getForObject(url + tProductId, ProductModel.class)).thenReturn(tProduct1);
		ProductModel result = productRepository.GetProductDetails(tProductId);
		assertNotNull(result);
		verify(restTemplate).getForObject(url + tProductId, ProductModel.class);
		System.out.print("The result is: " + result);
		System.out.print("Array to list is: " + Arrays.asList(tSimilarProducts));
		assertThat(result).usingRecursiveComparison().isEqualTo(tProduct1);
	}
}
