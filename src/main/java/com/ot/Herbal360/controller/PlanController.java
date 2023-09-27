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
import org.springframework.web.bind.annotation.RestController;

import com.ot.Herbal360.dto.Plan;
import com.ot.Herbal360.dto.ResponseStructure;
import com.ot.Herbal360.service.PlanService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/plan")
@CrossOrigin(origins = "*")
public class PlanController {

	@Autowired
	private PlanService planService;
	
	@ApiOperation(value = "Save Plan", notes = "Input is Plan Object and return Plan object")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "CREATED"),
			@ApiResponse(code = 409, message = "Plan Already Exist") })
	@PostMapping(value = "/save", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<ResponseStructure<Plan>> savePlan(@RequestBody @Validated Plan plan) {
		return planService.savePlan(plan);
	}
	
	@ApiOperation(value = "Fetch Plan by id", notes = "Input Is Id Of The Plan Object and return Plan Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Plan>> findPlanById(@PathVariable long id) {
		return planService.getPlanById(id);
	}
	
	@ApiOperation(value = "Fetch All Plan", notes = "Return The List Of Plans")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Plans Object") })
	@GetMapping(value = "/getallplan", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<List<Plan>>> getAllPlans() {
		return planService.getAllPlan();
	}
	
	@ApiOperation(value = "Delete Plan Object", notes = "Input Is Id Of The Plan Object And Output Is String")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/deleteplan/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<String>> deletePlanById(@PathVariable long id) {
		return planService.deletePlanById(id);
	}
	
	@ApiOperation(value = "Update Plan Object", notes = "Input Is Plan Object And Return Updated Plan Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/updateplan", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Plan>> updatePlan(@RequestBody @Validated Plan plan) {
		return planService.updatePlan(plan);
	}
	
	@ApiOperation(value = "Fetch Plan By Name", notes = "Input Is Name Of The Plan Object And Return Plan Object With Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Found"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/getplanbyname/{name}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Plan>> findPlanByName(@PathVariable String name) {
		return planService.getPlanByName(name);
	}
	
	@ApiOperation(value = "Fetch All Plans With Pagination And Sort", notes = "Return The List Of Plans With Pagination And Sort")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Fetched All The Plans Object") })
	@GetMapping(value = "/getAllPlans/{offset}/{pageSize}/{field}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseStructure<Page<Plan>>> getPlansWithPaginationAndSort(@PathVariable int offset,
			@PathVariable int pageSize, @PathVariable String field) {
		return planService.getPlansWithPaginationAndSorting(offset, pageSize, field);
	}

}
