package com.ot.Herbal360.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ot.Herbal360.dao.CustomerDao;
import com.ot.Herbal360.dao.PlanDao;
import com.ot.Herbal360.dto.Customer;
import com.ot.Herbal360.dto.Plan;
import com.ot.Herbal360.dto.ResponseStructure;
import com.ot.Herbal360.exception.DataNotFoundException;
import com.ot.Herbal360.exception.DuplicateDataEntryException;
import com.ot.Herbal360.exception.EmailIdNotFoundException;
import com.ot.Herbal360.exception.IdNotFoundException;
import com.ot.Herbal360.exception.PhoneNumberNotFoundException;
import com.ot.Herbal360.util.EmailSender;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private PlanDao planDao;

	@Autowired
	private EmailSender emailSender;

	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(Customer customer, long planId) {

		ResponseStructure<Customer> responseStructure = new ResponseStructure<>();

		if (customerDao.getCustomerByEmail(customer.getEmail()) != null
				|| customerDao.getCustomerByPhone(customer.getPhone()) != null) {

			throw new DuplicateDataEntryException("Customer Already Exist's");

		} else {
			Plan plan = planDao.getPlanById(planId);

			if (plan != null) {
				customer.setPlan(plan);
				int otp = (int) (Math.random() * (9999 - 1000) + 1000);
				System.out.println(otp);
				String otp1 = "CUSTOMER-" + otp;
				customer.setUuid(otp1);
				customer.setJoiningTime(LocalDate.now());
				customer.setEndingTime(customer.getJoiningTime().plusDays(plan.getDuration()));
				responseStructure.setStatus(HttpStatus.CREATED.value());
				responseStructure.setMessage("Customer Saved Successfully");
				responseStructure.setData(customerDao.saveCustomer(customer));
				emailSender.sendSimpleEmail(customer.getEmail(),
						"Greetings \nYour Profile in Herbal 360 Has Been Created.\nYou Can Now Choose Your Plan \nThank You.",
						"Hello " + customer.getName());
				return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
			} else {
				throw new IdNotFoundException("Plan Id " + planId + " Not Found ");
			}

		}
	}

	public ResponseEntity<ResponseStructure<Customer>> getCustomerById(long id) {
		ResponseStructure<Customer> responseStructure = new ResponseStructure<>();
		Customer customer = customerDao.getCustomerById(id);
		if (customer != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Customer Details By Id");
			responseStructure.setData(customer);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Customer ID " + id + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomer() {
		ResponseStructure<List<Customer>> responseStructure = new ResponseStructure<>();
		List<Customer> list = customerDao.getAllCustomer();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Customer Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Customer Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteCustomerById(long id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Customer customer = customerDao.getCustomerById(id);
		if (customer != null) {
			customerDao.deleteCustomer(customer);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Customer Of Id " + id + " Data Deleted");
			responseStructure.setData("Customer Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Customer Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(Customer customer) {
		ResponseStructure<Customer> responseStructure = new ResponseStructure<>();
		Customer customer1 = customerDao.getCustomerById(customer.getId());
		if (customer1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Customer Updated Successfully");

			responseStructure.setData(customerDao.saveCustomer(customer));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Customer Id " + customer.getId() + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<Customer>>> getCustomerByName(String customerName) {
		ResponseStructure<List<Customer>> responseStructure = new ResponseStructure<>();
		List<Customer> customers = customerDao.getCustomerByName(customerName);
		for (Customer customer : customers) {
			if (customer.getName().equalsIgnoreCase(customerName) && customers.size() > 0) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Fetched Customer By Name");
				responseStructure.setData(customers);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new DataNotFoundException("Customer Data Not Present");
			}
		}
		throw new DataNotFoundException("Customer Data Not Present");
	}

	public ResponseEntity<ResponseStructure<Customer>> getCustomerByEmail(String email) {
		Customer customer = customerDao.getCustomerByEmail(email);
		if (customer != null) {
			ResponseStructure<Customer> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Customer By Email-Id");
			responseStructure.setData(customer);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new EmailIdNotFoundException("Customer-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Customer>> getCustomerByPhone(String phone) {
		Customer customer = customerDao.getCustomerByPhone(phone);
		if (customer != null) {
			ResponseStructure<Customer> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Customer By PhoneNumber");
			responseStructure.setData(customer);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new PhoneNumberNotFoundException("Customer-PhoneNumber : " + phone + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Page<Customer>>> getCustomersWithPaginationAndSorting(int offset,
			int pageSize, String field) {
		ResponseStructure<Page<Customer>> responseStructure = new ResponseStructure<>();
		Page<Customer> page = customerDao.findCustomersWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Customer Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Customer Data Not Present");
		}
	}

}
