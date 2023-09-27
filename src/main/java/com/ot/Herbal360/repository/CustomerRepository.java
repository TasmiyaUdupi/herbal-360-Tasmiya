package com.ot.Herbal360.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.Herbal360.dto.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

	public Optional<Customer> findByEmail(String email);

	public Optional<Customer> findByPhone(String phone);

	public Optional<List<Customer>> findByName(String name);

}
