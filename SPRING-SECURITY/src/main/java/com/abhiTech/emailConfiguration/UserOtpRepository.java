package com.abhiTech.emailConfiguration;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOtpRepository extends CrudRepository<UserOtp, String> {

	UserOtp findByEmail(String email);

}
