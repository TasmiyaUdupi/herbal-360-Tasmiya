package com.ot.Herbal360.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.ot.Herbal360.dto.User;
import com.ot.Herbal360.repository.UserRepository;

@Repository
public class UserDao {

	@Autowired
	private UserRepository userRepository;

	public User saveAdmin(User user) {
		return userRepository.save(user);
	}

	public User getAdminById(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	public List<User> getAllAdmin() {
		return userRepository.getAllAdmin();
	}
	
	public Page<User> findUsersWithPaginationAndSorting(int offset, int pageSize, String field) {
		Page<User> admins = userRepository.findUsersWithPaginationAndSorting(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return admins;
	}


	public void deleteAdmin(User user) {
		userRepository.delete(user);
	}

	public User getUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	public User getUserByPhone(String phone) {
		Optional<User> user=userRepository.findByPhone(phone);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}

	public List<User> getUserByName(String userName) {
		Optional<List<User>> user=userRepository.findByUserName(userName);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}
	
	
	public User getUserByOtp(int otp) {
		Optional<User> user=userRepository.findByOtp(otp);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}
	
	public User getUserByUuid(String uuid) {
		Optional<User> user=userRepository.findByUuid(uuid);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}
}
