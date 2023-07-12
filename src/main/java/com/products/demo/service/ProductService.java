package com.products.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.products.demo.controller.ProductController;
import com.products.demo.exception.SimilarProductsNotFoundException;
import com.products.demo.model.ProductModel;
import com.products.demo.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductService {
	
	private ProductRepository repository = new ProductRepository();
	
	public List<ProductModel> getSimilarProducts(String productId) throws SimilarProductsNotFoundException {
		List<ProductModel> products = new ArrayList<ProductModel>();
		List<String> similarProducts = repository.getSimilarProducts(productId);		
		if(similarProducts != null) {
			for(String id : similarProducts ) {				
				ProductModel product = this.GetProductDetails(id);				
				if(product != null) {
					products.add(product);
				}
			}
			return products;
		}
		log.error("Similar products not found for product id: " + productId);
		throw new SimilarProductsNotFoundException("Similar products not found for product id: " + productId);
	}
	
	public ProductModel GetProductDetails(String productId) {
		try {			
			return repository.GetProductDetails(productId);
		} catch (Exception e) {		
			log.error("An error ocurred while calling external service for product details with product id " + productId);
			return null;
		}		
	}

}
