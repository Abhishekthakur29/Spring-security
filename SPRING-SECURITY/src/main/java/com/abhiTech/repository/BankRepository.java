package com.abhiTech.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abhiTech.model.Customer;

@Repository
public interface BankRepository extends CrudRepository<Customer, Integer> {
	Customer findByEmail(String email);
}
