package com.ot.Herbal360.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ot.Herbal360.dao.ProductDao;
import com.ot.Herbal360.dto.Product;
import com.ot.Herbal360.dto.ResponseStructure;
import com.ot.Herbal360.exception.DataNotFoundException;
import com.ot.Herbal360.exception.IdNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public ResponseEntity<ResponseStructure<Product>> saveProduct(Product product) {

		ResponseStructure<Product> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Product Saved Successfully");
		responseStructure.setData(productDao.saveProduct(product));
		return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Product>> getProductById(long id) {

		ResponseStructure<Product> responseStructure = new ResponseStructure<>();
		Product product = productDao.getProductById(id);
		if (product != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Product Details By Id");
			responseStructure.setData(product);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Product ID " + id + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Product>>> getAllProduct() {
		ResponseStructure<List<Product>> responseStructure = new ResponseStructure<>();
		List<Product> list = productDao.getAllProduct();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Product Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Product Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Page<Product>>> findProductsWithPaginationAndSorting(int offset,
			int pageSize, String field) {

		ResponseStructure<Page<Product>> responseStructure = new ResponseStructure<>();
		Page<Product> page = productDao.findProductsWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Product Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Product Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteProductById(long id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Product product = productDao.getProductById(id);
		if (product != null) {
			productDao.deleteProduct(product);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Product Of Id " + id + " Data Deleted");
			responseStructure.setData("Product Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Product Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Product>> getProductByName(String name) {
		ResponseStructure<Product> responseStructure = new ResponseStructure<>();
		Product product = productDao.getProductByName(name);
		if (product.getName().equalsIgnoreCase(name) && product.getName() != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Product By Name");
			responseStructure.setData(product);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Product Data Not Present");
		}
	}
	
	public ResponseEntity<ResponseStructure<Product>> updateProduct(Product product) {
		ResponseStructure<Product> responseStructure = new ResponseStructure<>();
		Product product1 = productDao.getProductById(product.getId());
		if (product1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Product Updated Successfully");

			responseStructure.setData(productDao.saveProduct(product));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Product Id " + product.getId() + ", Not Found");
		}
	}
}
