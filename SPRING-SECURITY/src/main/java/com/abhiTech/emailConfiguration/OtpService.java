package com.abhiTech.emailConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class OtpService {

	private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

	@Autowired
	private UserOtpRepository otpRepository;

	private Random random = new Random();

	public UserOtp generateOtp(String email) {
		UserOtp userOtp = new UserOtp();
		try {
			// Check if an OTP already exists for the email
			UserOtp existingOtp = otpRepository.findByEmail(email);

			// If an existing OTP is found, delete it
			if (existingOtp != null) {
				if (existingOtp.getPer_day_otp_limt() <= 3) {
					String otp = String.format("%06d", random.nextInt(1000000));
					userOtp.setExpirationTime(LocalDateTime.now().plusMinutes(10));
					userOtp.setCreattionTime(LocalDateTime.now());
					userOtp.setEmail(email);
					userOtp.setOtp(otp);
					userOtp.setPer_day_otp_limt(existingOtp.getPer_day_otp_limt() + 1);
					userOtp.setId(existingOtp.getId());
					UserOtp userOtp2 = otpRepository.save(userOtp);
					if (userOtp2 != null) {
						userOtp.setStatus("SUCCESS");
						return userOtp;
					}
				}
				userOtp.setStatus("Exceed");
				userOtp.setEmail(email);
				return userOtp;
			} else {
				// Generate a new OTP
				String otp = String.format("%06d", random.nextInt(1000000));
				userOtp.setId(UUID.randomUUID().toString());
				userOtp.setOtp(otp);
				userOtp.setEmail(email);
				userOtp.setPer_day_otp_limt(1);
				userOtp.setCreattionTime(LocalDateTime.now());
				userOtp.setExpirationTime(LocalDateTime.now().plusMinutes(10));

				UserOtp save = otpRepository.save(userOtp);
				if (save != null) {
					userOtp.setStatus("SUCCESS");
					return userOtp;
				}
			}

		} catch (Exception e) {
			logger.error("Failed to generate OTP for email: {}", email, e);
		}
		return userOtp;
	}

	public boolean verifyOtp(String email, String enteredOtp) {
		try {
			UserOtp userOtp = otpRepository.findByEmail(email);

			if (userOtp != null && userOtp.getOtp().equals(enteredOtp)
					&& LocalDateTime.now().isBefore(userOtp.getExpirationTime())) {
				return true;
			}

			return false;
		} catch (Exception e) {
			logger.error("Failed to verify OTP for email: {}", email, e);
			return false;
		}
	}
}
