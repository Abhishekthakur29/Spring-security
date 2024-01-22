package com.abhiTech.captchaConfig;

import java.time.LocalDateTime;

public class CaptchaResponse {
	private String captchaBase64 = null;
	private boolean verified = false;
	private String statuscode;
	private LocalDateTime creationTime;

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public String getCaptchaBase64() {
		return captchaBase64;
	}

	public void setCaptchaBase64(String captchaBase64) {
		this.captchaBase64 = captchaBase64;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

}
