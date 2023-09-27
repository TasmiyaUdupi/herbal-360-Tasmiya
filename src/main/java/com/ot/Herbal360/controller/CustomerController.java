package com.ot.Herbal360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ot.Herbal360.dto.Customer;
import com.ot.Herbal360.dto.ResponseStructure;
import com.ot.Herbal360.service.CustomerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@ApiOperation(value = "Save Customer", notes = "Input is Customer Object and return Customer object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Customer Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(@RequestBody @Validated Customer customer,
		@RequestParam long planId) {
		return customerService.saveCustomer(customer, planId);
	}

	@ApiOperation(value = "Fetch Customer by id", notes = "Input Is Id Of The Customer Object and return Customer Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> findCustomerById(@PathVariable long id) {
		return customerService.getCustomerById(id);
	}

	@ApiOperation(value = "Fetch All Customer", notes = "Return The List Of Customers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Customers Object") })
	@GetMapping(value = "/getallcustomer", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomers() {
		return customerService.getAllCustomer();
	}

	@ApiOperation(value = "Delete Customer Object", notes = "Input Is Id Of The Customer Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deletecustomer/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteCustomerById(@PathVariable long id) {
		return customerService.deleteCustomerById(id);
	}

	@ApiOperation(value = "Update Customer Object", notes = "Input Is Customer Object And Return Updated Customer Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updatecustomer", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(@RequestBody @Validated Customer customer) {
		return customerService.updateCustomer(customer);
	}

	@ApiOperation(value = "Fetch Customer By Name", notes = "Input Is Name Of The Customer Object And Return Customer Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getcustomerbyname/{customerName}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Customer>>> findCustomerByName(@PathVariable String customerName) {
		return customerService.getCustomerByName(customerName);
	}

	@ApiOperation(value = "Fetch Customer By Email-Id", notes = "Input Is Email-Id Of The Customer Object And Return Customer Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/email/{email}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> findCustomerByEmail(@PathVariable String email) {
		return customerService.getCustomerByEmail(email);
	}

	@ApiOperation(value = "Fetch Customer By PhoneNumber", notes = "Input Is PhoneNumber Of The Customer Object And Return Customer Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/phone/{phone}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Customer>> findCustomerByPhone(@PathVariable String phone) {
		return customerService.getCustomerByPhone(phone);
	}

	@ApiOperation(value = "Fetch All Customers With Pagination And Sort", notes = "Return The List Of Customers With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Customers Object") })
	@GetMapping(value = "/getAllCustomers/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<Customer>>> getCustomersWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		return customerService.getCustomersWithPaginationAndSorting(offset, pageSize, field);
	}
}
