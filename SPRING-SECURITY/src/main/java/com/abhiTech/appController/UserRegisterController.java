package com.abhiTech.appController;

import org.springframework.web.bind.annotation.RestController;

import com.abhiTech.model.Customer;
import com.abhiTech.repository.BankRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserRegisterController {

	@Autowired
	BankRepository bankRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody Customer customer) {
		Customer savedCustomer = null;
		ResponseEntity<Object> response = null;
		try {
			Customer email = bankRepository.findByEmail(customer.getEmail());
			if (email == null) {
				String encodedPwd = passwordEncoder.encode(customer.getPwd());
				customer.setPwd(encodedPwd);
				savedCustomer = bankRepository.save(customer);
				if (savedCustomer.getId() > 0) {
					response = ResponseEntity.status(HttpStatus.CREATED).body("User registeration is successful");
				}
			} else {
				response = ResponseEntity.status(HttpStatus.CONFLICT)
						.body("User Already Register try with another Email Id");
			}
		} catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception occured due to " + e.getMessage());

		}

		return response;
	}

}
