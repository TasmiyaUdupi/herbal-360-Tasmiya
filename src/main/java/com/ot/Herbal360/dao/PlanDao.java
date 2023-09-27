package com.ot.Herbal360.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ot.Herbal360.dto.Plan;
import com.ot.Herbal360.repository.PlanRepository;

@Repository
public class PlanDao {

	@Autowired
	private PlanRepository planRepository;

	public Plan savePlan(Plan plan) {
		return planRepository.save(plan);
	}

	public Plan getPlanById(long id) {
		Optional<Plan> plan = planRepository.findById(id);
		if (plan.isPresent()) {
			return plan.get();
		} else {
			return null;
		}
	}

	public List<Plan> getAllPlan() {
		return planRepository.findAll();
	}

	public Page<Plan> findPlansWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<Plan> plans = planRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return plans;
	}
	
	public void deletePlan(Plan plan) {
		planRepository.delete(plan);
	}
	
	public Plan getPlanByName(String name) {
		Optional<Plan> plan=planRepository.findByName(name);
		if(plan.isPresent()) {
			return plan.get();
		}else {
			return null;
		}
	}

}
