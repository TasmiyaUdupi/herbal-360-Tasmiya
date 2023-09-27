package com.ot.Herbal360.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ot.Herbal360.dao.PlanDao;
import com.ot.Herbal360.dto.Plan;
import com.ot.Herbal360.dto.ResponseStructure;
import com.ot.Herbal360.exception.DataNotFoundException;
import com.ot.Herbal360.exception.IdNotFoundException;

@Service
public class PlanService {

	@Autowired
	private PlanDao planDao;

	public ResponseEntity<ResponseStructure<Plan>> savePlan(Plan plan) {

		ResponseStructure<Plan> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("Plan Saved Successfully");
		responseStructure.setData(planDao.savePlan(plan));
		return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Plan>> getPlanById(long id) {
		ResponseStructure<Plan> responseStructure = new ResponseStructure<>();
		Plan plan = planDao.getPlanById(id);
		if (plan != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Plan Details By Id");
			responseStructure.setData(plan);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Plan ID " + id + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<List<Plan>>> getAllPlan() {
		ResponseStructure<List<Plan>> responseStructure = new ResponseStructure<>();
		List<Plan> list = planDao.getAllPlan();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Plan Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Plan Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Page<Plan>>> getPlansWithPaginationAndSorting(int offset, int pageSize,
			String field) {
		ResponseStructure<Page<Plan>> responseStructure = new ResponseStructure<>();
		Page<Plan> page = planDao.findPlansWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of Plan Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Plan Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deletePlanById(long id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		Plan plan = planDao.getPlanById(id);
		if (plan != null) {
			planDao.deletePlan(plan);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Plan Of Id " + id + " Data Deleted");
			responseStructure.setData("Plan Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Plan Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<Plan>> getPlanByName(String name) {
		ResponseStructure<Plan> responseStructure = new ResponseStructure<>();
		Plan plan = planDao.getPlanByName(name);

		if (plan.getName().equalsIgnoreCase(name) && plan.getName() != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Plan By Name");
			responseStructure.setData(plan);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("Plan Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Plan>> updatePlan(Plan plan) {
		ResponseStructure<Plan> responseStructure = new ResponseStructure<>();
		Plan plan1 = planDao.getPlanById(plan.getId());
		if (plan1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Plan Updated Successfully");

			responseStructure.setData(planDao.savePlan(plan));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Plan Id " + plan.getId() + ", Not Found");
		}
	}

}
