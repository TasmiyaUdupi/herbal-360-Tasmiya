package com.ot.Herbal360.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ot.Herbal360.dto.Customer;
import com.ot.Herbal360.repository.CustomerRepository;

@Repository
public class CustomerDao {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Customer getCustomerById(long id){
		Optional<Customer> customer=customerRepository.findById(id);
		if(customer.isPresent()) {
			return customer.get();
		}else {
			return null;
		}
	}
	
	public List<Customer> getAllCustomer(){
		return customerRepository.findAll();
	}
	
	public Page<Customer> findCustomersWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<Customer> customers = customerRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return customers;
	}
	
	public Customer getCustomerByEmail(String email) {
		Optional<Customer> customer=customerRepository.findByEmail(email);
		if(customer.isPresent()) {
			return customer.get();
		}else {
			return  null;
		}
	}
	
	public Customer getCustomerByPhone(String phone) {
		Optional<Customer> customer=customerRepository.findByPhone(phone);
		if(customer.isPresent()) {
			return customer.get();
		}else {
			return null;
		}
	}
	
	public List<Customer> getCustomerByName(String customerName) {
		Optional<List<Customer>> customer=customerRepository.findByName(customerName);
		if(customer.isPresent()) {
			return customer.get();
		}else {
			return null;
		}
	}
	
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}
}

