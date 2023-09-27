package com.ot.Herbal360.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ot.Herbal360.dto.Product;
import com.ot.Herbal360.repository.ProductRepository;

@Repository
public class ProductDao {

	@Autowired
	private ProductRepository productRepository;
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Product getProductById(long id) {
		Optional<Product> product=productRepository.findById(id);
		if(product.isPresent()) {
			return product.get();
		}else {
			return null;
		}
	}
	
	public List<Product> getAllProduct(){
		return productRepository.findAll();
	}
	
	public Page<Product> findProductsWithPaginationAndSorting(int offset,int pageSize,String field){
		Page<Product> products = productRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return products;
	}
	
	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}
	
	public Product getProductByName(String name) {
		Optional<Product> product= productRepository.findByName(name);
		if(product.isPresent()) {
			return product.get();
		}else {
			return null;
		}
	}
}
