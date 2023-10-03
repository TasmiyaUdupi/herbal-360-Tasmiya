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

import com.ot.Herbal360.dto.PlanProduct;
import com.ot.Herbal360.dto.PlanProductDays;
import com.ot.Herbal360.dto.ResponseStructure;
import com.ot.Herbal360.service.PlanProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/planProduct")
@CrossOrigin(origins = "*")
public class PlanProductController {

	@Autowired
	private PlanProductService planProductService;

	@ApiOperation(value = "Save PlanProduct", notes = "Input Is PlanProduct Object and Return PlanProduct Object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "PlanProduct Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<PlanProduct>> savePlanProduct(@RequestBody PlanProduct planProduct,
			PlanProductDays planProductDays, @RequestParam long custId) {
		return planProductService.savePlanProduct(planProduct, planProductDays, custId);
	}

	@ApiOperation(value = "Fetch PlanProduct by id", notes = "Input Is Id Of The PlanProduct Object and return PlanProduct Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<PlanProduct>> findPlanProductById(@PathVariable long id) {
		return planProductService.getPlanProductById(id);
	}

	@ApiOperation(value = "Fetch All PlanProduct", notes = "Return The List Of PlanProducts")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The PlanProducts Object") })
	@GetMapping(value = "/getallPlanProduct", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<PlanProduct>>> getAllPlanProducts() {
		return planProductService.getAllPlanProduct();
	}

	@ApiOperation(value = "Delete PlanProduct Object", notes = "Input Is Id Of The PlanProduct Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deletePlanProduct/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deletePlanProductById(@PathVariable long id) {
		return planProductService.deletePlanProduct(id);
	}

	@ApiOperation(value = "Update PlanProduct Object", notes = "Input Is PlanProduct Object And Return Updated PlanProduct Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updatePlanProduct", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<PlanProduct>> updatePlanProduct(
			@RequestBody @Validated PlanProduct planProduct) {
		return planProductService.updatePlanProduct(planProduct);
	}

	@ApiOperation(value = "Fetch All PlanProducts With Pagination And Sort", notes = "Return The List Of PlanProducts With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The PlanProducts Object") })
	@GetMapping(value = "/getAllPlanProducts/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<PlanProduct>>> getPlanProductsWithPaginationAndSort(
			@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
		return planProductService.findPlanProductWithPaginationAndSorting(offset, pageSize, field);
	}
}
