package com.abhiTech.emailConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	public String sendEmail(String toEmail, String otp) {
		String signature = "\n\nBest regards,\nAbhishek Kumar";
		String emailSubject = "OTP(One Time password || Login)";
		logger.info("Email OTP :: %d", otp);
		logger.info("Email :: %d", toEmail);
		StringBuffer emailBody = new StringBuffer();
		emailBody.append("");
		emailBody.append("\n");
		emailBody.append("OTP(One Time Password): ");
		emailBody.append(otp);
		emailBody.append(" is valid for 10 minutes. For the Security concern Please don't Share OTP to anyone.");
		emailBody.append("Thanks for registering with us.\n Happy Learning");
		emailBody.append(signature);
		SimpleMailMessage message = new SimpleMailMessage();
		try {
			message.setTo(toEmail);
			message.setSubject(emailSubject);
			message.setText(emailBody.toString());
			javaMailSender.send(message);
			return "SUCCESS";
		} catch (Exception e) {
			logger.error("Failed to send OTP for email: {}", e);
		}
		return "FAILED";
	}
}
