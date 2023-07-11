package com.products.demo.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.products.demo.model.ProductModel;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

public class ProductRepository {
	
	static final String url = "http://localhost:3001/product/";
	
	private RestTemplate restTemplate;
	
	public ProductRepository() {
		this.restTemplate = new RestTemplate();
	}
	
	@Autowired
	private Environment env;

	public List<String> getSimilarProducts(String productId) {
		//String uri = env.getProperty("mock.service.url") + productId + "/similarids";
		String uri = url + productId + "/similarids";
		System.out.print("uri is: " + uri + "\n");
		//RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<String[]> responseEntity = this.restTemplate.getForEntity(uri, String[].class);
		System.out.print("Response Entity: " + responseEntity);
		List<String> result = Arrays.asList(responseEntity.getBody());
		return result;		
	}

	public ProductModel GetProductDetails(String productId) {
		//String uri = env.getProperty("mock.service.url") + productId;
		System.out.print("Llego aqui");
		String uri = url + productId;
		//RestTemplate restTemplate = new RestTemplate();
		ProductModel result = this.restTemplate.getForObject(uri, ProductModel.class);
		return result;
	}

}
