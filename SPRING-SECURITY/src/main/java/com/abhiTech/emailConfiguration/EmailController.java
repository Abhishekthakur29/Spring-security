package com.abhiTech.emailConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
	Logger logger = LoggerFactory.getLogger(EmailController.class);

	@Autowired
	private OtpService otpService;

	@Autowired
	private EmailService emailService;

	@PostMapping("/send-otp")
	public ResponseEntity<Object> generateOtp(@RequestBody EmailModel emailModel) {
		ResponseEntity<Object> responseEntity = null;
		SuccessResponse response = new SuccessResponse();

		UserOtp otpStatus = otpService.generateOtp(emailModel.getTo());

		if (otpStatus != null) {
			if (otpStatus.getStatus().equals("Exceed")) {
				response.setMessage("Daily OTP limt exceed");
				response.setStatusCode("OTP_406");
				responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
				return responseEntity;
			} else {
				String sendEmail = emailService.sendEmail(otpStatus.getEmail(), otpStatus.getOtp());
				if (sendEmail.equals("SUCCESS")) {
					response.setMessage("OTP Sent User Email");
					response.setStatusCode("OTP_202");
					responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
					return responseEntity;
				}
			}
		} else {
			response.setMessage("Failed to sent OTP User Email");
			response.setStatusCode("OTP_500");
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		return responseEntity;
	}

	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestBody OtpVerificationRequest request) {
		// Retrieve the stored OTP for the user

		boolean otpVerified = otpService.verifyOtp(request.getEmail(), request.getEnteredOTP());
		// Verify the entered OTP
		if (otpVerified) {
			return "OTP verification successful";
		} else {
			return "Invalid OTP. Verification failed";
		}
	}
}
