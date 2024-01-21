package com.abhiTech.appController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyLoanControoler {
	Logger logger =LoggerFactory.getLogger(MyLoanControoler.class);
	@GetMapping("/myLoans")
	public ResponseEntity<Object> getmyLoan(){
		ResponseEntity<Object> response = null;
		try {
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body("You loan is under process ");
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		return response;
	}

}
