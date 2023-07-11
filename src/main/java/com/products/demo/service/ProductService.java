package com.products.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.products.demo.exception.SimilarProductsNotFoundException;
import com.products.demo.model.ProductModel;
import com.products.demo.repository.ProductRepository;

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
		throw new SimilarProductsNotFoundException("Similar products not found for product id: " + productId);
	}
	
	public ProductModel GetProductDetails(String productId) {
		try {			
			return repository.GetProductDetails(productId);
		} catch (Exception e) {			
			return null;
		}		
	}

}
