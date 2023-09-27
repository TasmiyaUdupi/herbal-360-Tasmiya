package com.ot.Herbal360.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ot.Herbal360.dto.User;
import com.ot.Herbal360.exception.InvalidCredentialException;
import com.ot.Herbal360.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optional = userRepository.findByEmail(email);
		CustomUserDetails customUserDetails = new CustomUserDetails();

		if (optional.isPresent()) {
			User user = optional.get();
			System.out.println(user.getRole());
			customUserDetails.setUser(user);
		} else {
			throw new InvalidCredentialException("Invalid User email:" + email + " or password");
		}

		return customUserDetails;
	}

}