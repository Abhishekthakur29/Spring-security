package com.abhiTech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abhiTech.model.Customer;
import com.abhiTech.repository.BankRepository;

@Service
public class EazyBankUserDetails implements UserDetailsService {

	@Autowired
	BankRepository bankRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String userName, password;
		List<GrantedAuthority> authorities;
		Customer customer = bankRepository.findByEmail(username);

		if (customer == null) {
			throw new UsernameNotFoundException("User not found the with the given EmailId ::> " + username);
		} else {
			userName = customer.getEmail();
			password = customer.getPwd();
			authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(customer.getRole()));
		}
		return new User(userName, password, authorities);
	}

}
