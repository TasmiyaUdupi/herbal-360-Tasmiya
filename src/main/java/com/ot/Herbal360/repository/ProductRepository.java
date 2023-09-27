package com.ot.Herbal360.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.Herbal360.dto.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	public Optional<Product> findByName(String name);

}
