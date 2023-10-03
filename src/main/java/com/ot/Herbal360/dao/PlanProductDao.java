package com.ot.Herbal360.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ot.Herbal360.dto.PlanProduct;
import com.ot.Herbal360.repository.PlanProductRepository;

@Repository
public class PlanProductDao {

	@Autowired
	private PlanProductRepository planProductRepository;

	public PlanProduct savePlanProduct(PlanProduct planProduct) {
		return planProductRepository.save(planProduct);
	}

	public PlanProduct getPlanProductById(long id) {
		Optional<PlanProduct> planProduct = planProductRepository.findById(id);
		if (planProduct.isPresent()) {
			return planProduct.get();
		} else {
			return null;
		}
	}
	
	public List<PlanProduct> getAllPlanProduct(){
		return planProductRepository.findAll();
	}

	public Page<PlanProduct> findPlanProductWithPaginationAndSorting(int offSet,int pageSize,String field){
		Page<PlanProduct> planProduct=planProductRepository.findAll(PageRequest.of(offSet, pageSize).withSort(Sort.by(field)));
		return planProduct;
	}
	
	public void deletePlanProduct(PlanProduct planProduct) {
		 planProductRepository.delete(planProduct);
	}
}
