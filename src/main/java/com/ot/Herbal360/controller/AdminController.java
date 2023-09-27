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

import com.ot.Herbal360.dto.ResponseStructure;
import com.ot.Herbal360.dto.User;
import com.ot.Herbal360.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Save Admin", notes = "Input is User Object and return Admin object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "User Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<User>> saveAdmin(@RequestBody @Validated User user) {
		return userService.saveAdmin(user);
	}

	@ApiOperation(value = "Fetch Admin by id", notes = "Input Is Id Of The Admin Object and return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> findAdminById(@PathVariable long id) {
		return userService.getAdminById(id);
	}

	@ApiOperation(value = "Fetch All Admin", notes = "Return The List Of Admins")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Admins Object") })
	@GetMapping(value = "/getalladmin", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<User>>> getAllAdmins() {
		return userService.getAllAdmin();
	}

	@ApiOperation(value = "Delete Admin Object", notes = "Input Is Id Of The Admin Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deleteadmin/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deleteAdminById(@PathVariable long id) {
		return userService.deleteAdminById(id);
	}

	@ApiOperation(value = "Update Admin Object", notes = "Input Is Admin Object And Return Updated Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updateadmin", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> updateAdmin(@RequestBody @Validated User user) {
		return userService.updateAdmin(user);
	}

	@ApiOperation(value = "Fetch Admin By Name", notes = "Input Is Name Of The Admin Object And Return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getadminbyname/{userName}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<User>>> findAdminByName(@PathVariable String userName) {
		return userService.getUserByName(userName);
	}

	@ApiOperation(value = "Fetch Admin By Email-Id", notes = "Input Is Email-Id Of The Admin Object And Return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/email/{email}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> findAdminByEmail(@PathVariable String email) {
		return userService.getUserByEmail(email);
	}

	@ApiOperation(value = "Fetch Admin By PhoneNumber", notes = "Input Is PhoneNumber Of The Admin Object And Return Admin Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/phone/{phone}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<User>> findAdminByPhone(@PathVariable String phone) {
		return userService.getUserByPhone(phone);
	}

	@ApiOperation(value = "Validate Admin By Email", notes = "Inputs are Admin email id and password and return Admin object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/validate/email/{email}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Object>> validateAdminByEmail(@PathVariable String email,
			@PathVariable String password) {
		return userService.validateUserByEmail(email, password);
	}

	@GetMapping(value = "/otp", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<User>> validateOtp(@RequestParam int otp) {
		return userService.validateOtp(otp);
	}

	@ApiOperation(value = "Validate Admin By Phone", notes = "Inputs are Admins PhoneNumber and Password and return Admin Object")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Fond") })
	@PutMapping(value = "/validate/phone/{phone}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Object>> validateAdminByPhone(@PathVariable String phone,
			@PathVariable String password) {
		return userService.validateUserByPhone(phone, password);
	}

	@ApiOperation(value = "Fetch All Admins With Pagination And Sort", notes = "Return The List Of Admins With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Admins Object") })
	@GetMapping(value = "/getAllAdmins/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<User>>> getAdminsWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		return userService.getAdminsWithPaginationAndSorting(offset, pageSize, field);
	}
}