package com.products.demo.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.products.demo.model.ProductModel;

public class ProductRepository {
	
	static final String URL = "http://localhost:3001/product/";
	
	private RestTemplate restTemplate;
	
	public ProductRepository() {
		this.restTemplate = new RestTemplate();
	}	

	public List<String> getSimilarProducts(String productId) {
		String uri = URL + productId + "/similarids";		
		ResponseEntity<String[]> responseEntity = this.restTemplate.getForEntity(uri, String[].class);		
		List<String> result = Arrays.asList(responseEntity.getBody());
		return result;		
	}

	public ProductModel GetProductDetails(String productId) {		
		String uri = URL + productId;
		ProductModel result = this.restTemplate.getForObject(uri, ProductModel.class);
		return result;
	}

}
