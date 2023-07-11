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
		System.out.print("Body is: " + similarProducts + "\n");
		if(similarProducts != null) {
			for(String id : similarProducts ) {
				System.out.print("Id is: " + id + "\n");
				ProductModel product = this.GetProductDetails(id);
				System.out.print("Product is: " + product + "\n");
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
			System.out.print("Id inside function is: " + productId + "\n");
			return repository.GetProductDetails(productId);
		} catch (Exception e) {
			System.out.print("Entered exception\n");
			System.out.print(e.getMessage());
			return null;
		}		
	}

}
