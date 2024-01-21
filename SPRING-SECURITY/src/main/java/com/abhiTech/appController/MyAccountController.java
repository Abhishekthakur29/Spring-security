package com.abhiTech.appController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyAccountController {

	@GetMapping("getMessage")
	public String getMessage() {
		return "Welcome to the Spring security Applicaiton";
	}
	
}
