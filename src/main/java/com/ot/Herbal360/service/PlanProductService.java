package com.ot.Herbal360.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ot.Herbal360.dao.CustomerDao;
import com.ot.Herbal360.dao.PlanDao;
import com.ot.Herbal360.dao.PlanProductDao;
import com.ot.Herbal360.dao.ProductDao;
import com.ot.Herbal360.dto.Customer;
import com.ot.Herbal360.dto.Plan;
import com.ot.Herbal360.dto.PlanProduct;
import com.ot.Herbal360.dto.PlanProductDays;
import com.ot.Herbal360.dto.Product;
import com.ot.Herbal360.dto.ResponseStructure;
import com.ot.Herbal360.exception.DataNotFoundException;
import com.ot.Herbal360.exception.IdNotFoundException;

@Service
public class PlanProductService {

	@Autowired
	private PlanProductDao planProductDao;

	@Autowired
	private PlanDao planDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CustomerDao customerDao;

	public ResponseEntity<ResponseStructure<PlanProduct>> savePlanProduct(PlanProduct planProduct,
			PlanProductDays planProductDays, long custId) {
		ResponseStructure<PlanProduct> responseStructure = new ResponseStructure<>();

		Plan plan = planDao.getPlanById(planProduct.getPlanId());
		Product product = productDao.getProductById(planProduct.getProductId());
		Customer customer = customerDao.getCustomerById(custId);
		if (customer != null) {
			if (plan != null) {
				if (product != null) {
					List<Integer> list = planProductDays.getDay();
					if (list.size() > 0) {
						for (Integer integer : list) {
							planProduct.setDays(integer);
							planProduct.setPlanId(plan.getId());
							planProduct.setProductId(product.getId());
							planProduct.setSheduledTime(customer.getJoiningTime().plusDays(integer));
							responseStructure.setStatus(HttpStatus.CREATED.value());
							responseStructure.setMessage("PlanProduct Saved Successfully");
							responseStructure.setData(planProductDao.savePlanProduct(planProduct));
						}
						return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
					} else {
						throw new DataNotFoundException(planProductDays.getDay() + " The Days Are Null");
					}
				} else {
					throw new IdNotFoundException("Product Id " + planProduct.getProductId() + " Not Found ");
				}
			} else {
				throw new IdNotFoundException("Plan Id " + planProduct.getPlanId() + " Not Found ");
			}
		} else {
			throw new IdNotFoundException("Customer Id " + custId + " Not Found ");
		}

	}

	public ResponseEntity<ResponseStructure<PlanProduct>> getPlanProductById(long id) {
		ResponseStructure<PlanProduct> responseStructure = new ResponseStructure<>();
		PlanProduct planProduct = planProductDao.getPlanProductById(id);
		if (planProduct != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched PlanProduct Details By Id");
			responseStructure.setData(planProduct);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("PlanProduct ID " + id + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<PlanProduct>>> getAllPlanProduct() {
		ResponseStructure<List<PlanProduct>> responseStructure = new ResponseStructure<>();
		List<PlanProduct> list = planProductDao.getAllPlanProduct();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of PlanProduct Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("PlanProduct Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Page<PlanProduct>>> findPlanProductWithPaginationAndSorting(int offSet,
			int pageSize, String field) {

		ResponseStructure<Page<PlanProduct>> responseStructure = new ResponseStructure<>();
		Page<PlanProduct> page = planProductDao.findPlanProductWithPaginationAndSorting(offSet, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of PlanProduct Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("PlanProduct Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deletePlanProduct(long id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		PlanProduct planProduct = planProductDao.getPlanProductById(id);
		if (planProduct != null) {
			planProductDao.deletePlanProduct(planProduct);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("PlanProduct Of Id " + id + " Data Deleted");
			responseStructure.setData("PlanProduct Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("PlanProduct Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<PlanProduct>> updatePlanProduct(PlanProduct planProduct) {
		ResponseStructure<PlanProduct> responseStructure = new ResponseStructure<>();
		PlanProduct planProduct1 = planProductDao.getPlanProductById(planProduct.getId());
		if (planProduct1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("PlanProduct Updated Successfully");
			responseStructure.setData(planProductDao.savePlanProduct(planProduct1));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("PlanProduct Id " + planProduct.getId() + ", Not Found");
		}
	}

}
