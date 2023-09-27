package com.ot.Herbal360.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ot.Herbal360.dto.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {

	public Optional<Plan> findByName(String name);

}
