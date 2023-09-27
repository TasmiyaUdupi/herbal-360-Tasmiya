package com.ot.Herbal360.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ot.Herbal360.dao.UserDao;
import com.ot.Herbal360.dto.ResponseStructure;
import com.ot.Herbal360.dto.User;
import com.ot.Herbal360.exception.DataNotFoundException;
import com.ot.Herbal360.exception.DuplicateDataEntryException;
import com.ot.Herbal360.exception.EmailIdNotFoundException;
import com.ot.Herbal360.exception.IdNotFoundException;
import com.ot.Herbal360.exception.InvalidCredentialException;
import com.ot.Herbal360.exception.PhoneNumberNotFoundException;
import com.ot.Herbal360.util.EmailSender;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private EmailSender emailSender;

	public ResponseEntity<ResponseStructure<User>> saveAdmin(User user) {

		ResponseStructure<User> responseStructure = new ResponseStructure<>();

		if (userDao.getUserByEmail(user.getEmail()) != null || userDao.getUserByPhone(user.getPhone()) != null) {

			throw new DuplicateDataEntryException("Admin Already Exist's");

		} else {
			user.setPassword(encoder.encode(user.getPassword()));
			user.setRole("ROLE_ADMIN");
			emailSender.sendSimpleEmail(user.getEmail(),
					"Greetings \nYour Profile in Herbal 360 Has Been Created.\nYou Can Now Choose Your Plan \nThank You.",
					"Hello " + user.getUserName());
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Admin Saved Successfully");
			responseStructure.setData(userDao.saveAdmin(user));
			return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
		}
	}

	public ResponseEntity<ResponseStructure<User>> getAdminById(long id) {
		ResponseStructure<User> responseStructure = new ResponseStructure<>();
		User user = userDao.getAdminById(id);
		if (user != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched ADMIN Details By Id");
			responseStructure.setData(user);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("ADMIN ID " + id + ", NOT FOUND");
		}
	}

	public ResponseEntity<ResponseStructure<List<User>>> getAllAdmin() {
		ResponseStructure<List<User>> responseStructure = new ResponseStructure<>();
		List<User> list = userDao.getAllAdmin();
		if (list.size() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of ADMIN Fetched");
			responseStructure.setData(list);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("ADMIN Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<Page<User>>> getAdminsWithPaginationAndSorting(int offset, int pageSize,
			String field) {
		ResponseStructure<Page<User>> responseStructure = new ResponseStructure<>();
		Page<User> page = userDao.findUsersWithPaginationAndSorting(offset, pageSize, field);
		if (page.getSize() > 0) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("All Details of ADMIN Fetched");
			responseStructure.setRecordCount(page.getSize());
			responseStructure.setData(page);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new DataNotFoundException("ADMIN Data Not Present");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteAdminById(long id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		User user = userDao.getAdminById(id);
		if (user != null) {
			userDao.deleteAdmin(user);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("ADMIN Of Id " + id + " Data Deleted");
			responseStructure.setData("ADMIN Data Deleted Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("ADMIN Id " + id + " Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<User>> updateAdmin(User user) {
		ResponseStructure<User> responseStructure = new ResponseStructure<>();
		User user1 = userDao.getAdminById(user.getId());
		if (user1 != null) {
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Admin Updated Successfully");
			user.setPassword(encoder.encode(user.getPassword()));
			user.setRole("ROLE_ADMIN");
			responseStructure.setData(userDao.saveAdmin(user));
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("ADMIN Id " + user.getId() + ", Not Found");
		}
	}

	public ResponseEntity<ResponseStructure<List<User>>> getUserByName(String userName) {
		ResponseStructure<List<User>> responseStructure = new ResponseStructure<>();
		List<User> users = userDao.getUserByName(userName);
		for (User user : users) {
			if (user.getUserName().equalsIgnoreCase(userName) && users.size() > 0) {
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Fetched Admin By Name");
				responseStructure.setData(users);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new DataNotFoundException("ADMIN Data Not Present");
			}
		}
		throw new DataNotFoundException("ADMIN Data Not Present");
	}

	public ResponseEntity<ResponseStructure<User>> getUserByEmail(String email) {
		User user = userDao.getUserByEmail(email);
		if (user != null) {
			ResponseStructure<User> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched ADMIN By Email-Id");
			responseStructure.setData(user);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new EmailIdNotFoundException("ADMIN-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<User>> getUserByPhone(String phone) {
		User user = userDao.getUserByPhone(phone);
		if (user != null) {
			ResponseStructure<User> responseStructure = new ResponseStructure<>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Fetched Admin By PhoneNumber");
			responseStructure.setData(user);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new PhoneNumberNotFoundException("User-PhoneNumber : " + phone + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> validateUserByEmail(String email, String password) {

		User user = userDao.getUserByEmail(email);
		if (user != null) {
			if (encoder.matches(password, user.getPassword())) {
				ResponseStructure<Object> responseStructure = new ResponseStructure<Object>();
				int otp = (int) (Math.random() * (9999 - 1000) + 1000);
				user.setOtp(otp);
				userDao.saveAdmin(user);
				emailSender.sendSimpleEmail(user.getEmail(),
						"Enter the Otp to Validate Your Self \n The Generated Otp " + otp, "Verify Your Otp");
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Successfully");
				responseStructure.setData(user);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new InvalidCredentialException("Invalid Password");
			}
		} else {
			throw new EmailIdNotFoundException("User-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<User>> validateOtp(int otp) {
		User user = userDao.getUserByOtp(otp);
		if (user != null) {
			ResponseStructure<User> responseStructure = new ResponseStructure<User>();
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Success");
			responseStructure.setData(user);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new InvalidCredentialException("Invalid OTP");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> validateUserByPhone(String phone, String password) {
		User user = userDao.getUserByPhone(phone);
		if (user != null) {
			if (encoder.matches(password, user.getPassword())) {
				ResponseStructure<Object> responseStructure = new ResponseStructure<Object>();
				int otp = (int) (Math.random() * (9999 - 1000) + 1000);
				user.setOtp(otp);
				userDao.saveAdmin(user);
				emailSender.sendSimpleEmail(user.getEmail(),
						"Enter the Otp to Validate Your Self \n The Generated Otp " + otp, "Verify Your Otp");
				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("Successfully");
				responseStructure.setData("Otp Sent Successfully");
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			} else {
				throw new InvalidCredentialException("Invalid Credential");
			}
		} else {
			throw new PhoneNumberNotFoundException("User-PhoneNumber : " + phone + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> verifyEmailBeforeUpdate(String email) {
		User user = userDao.getUserByEmail(email);
		if (user != null) {
			ResponseStructure<Object> responseStructure = new ResponseStructure<>();
			String uuid = UUID.randomUUID().toString();
			String partOfUuid = uuid.substring(0, 11);
			if (partOfUuid.contains("-")) {
				String replace = partOfUuid.replace("-", "");
				user.setUuid(replace);
				userDao.saveAdmin(user);
				emailSender.sendSimpleEmail(user.getEmail(),
						"Enter the Unique to Validate Your Account \n The Generated Unique ID " + replace,
						"Verify Your Unique Id Before You Change YOur Password");
			} else {
				user.setUuid(partOfUuid);
				userDao.saveAdmin(user);
				emailSender.sendSimpleEmail(user.getEmail(),
						"Enter the Unique to Validate Your Account \n The Generated Unique ID " + partOfUuid,
						"Verify Your Unique Id Before You Change YOur Password");
			}
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Verify Admin By Email-Id");
			responseStructure.setData("Uuid Send To User Email-Id Successfully");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new EmailIdNotFoundException("User-Email : " + email + ", NOT Found");
		}
	}

	public ResponseEntity<ResponseStructure<Object>> updatePasswordByUuid(String uuid, String newPassword) {
		ResponseStructure<Object> responseStructure = new ResponseStructure<>();
		User user = userDao.getUserByUuid(uuid);
		if (user != null) {
			user.setPassword(encoder.encode(newPassword));
			userDao.saveAdmin(user);
			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Password Reset");
			responseStructure.setData("Successfully Password Updated");
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new InvalidCredentialException("User-Uuid : " + uuid + ", Not Match");
		}
	}

}