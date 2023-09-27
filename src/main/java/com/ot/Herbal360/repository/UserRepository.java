package com.ot.Herbal360.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ot.Herbal360.dto.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByEmail(String email);

	public Optional<User> findByPhone(String phone);

	public Optional<List<User>> findByUserName(String userName);

	public Optional<User> findByOtp(int otp);

	public Optional<User> findByUuid(String uuid);

	@Query(value = "SELECT u FROM User u WHERE role = 'ROLE_ADMIN'")
	public List<User> getAllAdmin();
	
	@Query(value = "SELECT u FROM User u WHERE role = 'ROLE_ADMIN'")
	public Page<User> findUsersWithPaginationAndSorting(PageRequest withSort);

}
