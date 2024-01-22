package com.abhiTech.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.abhiTech.model.Customer;
import com.abhiTech.repository.BankRepository;

@Component
public class BankAuthenticationProvider implements AuthenticationProvider {

	Logger logger = LoggerFactory.getLogger(BankAuthenticationProvider.class);

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		Customer customer = bankRepository.findByEmail(userName);
		logger.info("Customer Details :: " + customer);
		if (customer != null) {
			if (passwordEncoder.matches(password, customer.getPwd())) {
				List<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(customer.getRole()));
				logger.info("👍 User login successful 👍");
				return new UsernamePasswordAuthenticationToken(userName, password, authorities);
			} else {
				logger.info("😒 Login unsuccessful 😒");
				throw new BadCredentialsException("Invalid password ");
			}
		} else {
			logger.info("😒 User Not Found 😒 " + userName);
			throw new BadCredentialsException("No User Register with given Id");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
