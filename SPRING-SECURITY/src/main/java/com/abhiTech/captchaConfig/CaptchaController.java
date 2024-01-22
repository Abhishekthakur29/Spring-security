package com.abhiTech.captchaConfig;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.apiclub.captcha.Captcha;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@SessionAttributes("captcha")
public class CaptchaController {
	private Logger logger = LoggerFactory.getLogger(CaptchaController.class);

	@Autowired
	private CaptchaService captchaService;

	@GetMapping("captcha")
	public ResponseEntity<Object> getCaptchaValue(HttpServletRequest request) {

		CaptchaResponse captchaResponse = new CaptchaResponse();

		// Check if a captcha already exists in the session
		CaptchaWrapper existingCaptcha = (CaptchaWrapper) request.getSession().getAttribute("captcha");

		if (existingCaptcha != null) {
			captchaResponse.setCaptchaBase64(convertImageToBase64(existingCaptcha.getCaptcha().getImage()));
			return ResponseEntity.status(HttpStatus.CREATED).body(captchaResponse);
		}

		Captcha captcha = captchaService.generateCaptcha();
		String answer = captcha.getAnswer();
		logger.info("Captcha value :: " + answer);
		request.getSession().setAttribute("captcha", new CaptchaWrapper(captcha, LocalDateTime.now()));
		captchaResponse.setCaptchaBase64(convertImageToBase64(captcha.getImage()));
		captchaResponse.setCreationTime(LocalDateTime.now());
		captchaResponse.setStatuscode("201");
		return ResponseEntity.status(HttpStatus.CREATED).body(captchaResponse);
	}

	@GetMapping("verifyCaptcha")
	public ResponseEntity<Object> verifyCaptcha(@RequestParam String userInput, HttpServletRequest request) {
		CaptchaResponse captchaResponse = new CaptchaResponse();

		CaptchaWrapper captchaWrapper = (CaptchaWrapper) request.getSession().getAttribute("captcha");

		if (captchaWrapper != null && !captchaWrapper.isVerified()) {

			// Implement your captcha verification logic here
			boolean captchaVerified = captchaService.verifyCaptcha(userInput);

			if (captchaVerified) {
				captchaWrapper.setVerified(true);
				// Additional logic after successful verification
				captchaResponse.setVerified(true);
				captchaResponse.setStatuscode("200");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(captchaResponse);
			} else {
				captchaResponse.setVerified(false);
				captchaResponse.setStatuscode("417");
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(captchaResponse);
			}
		} else {
			captchaResponse.setVerified(false);
			captchaResponse.setStatuscode("417");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(captchaResponse);
		}
	}

	private String convertImageToBase64(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
		} catch (IOException e) {
			// Handle the exception appropriately
			e.printStackTrace();
		}
		byte[] imageBytes = baos.toByteArray();
		return Base64.getEncoder().encodeToString(imageBytes);
	}

}
