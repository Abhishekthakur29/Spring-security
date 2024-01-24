package com.abhiTech.emailConfiguration;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserOtp {

	@Id
	private String id;
	private String email;
	private String otp;
	private LocalDateTime expirationTime;
	private LocalDateTime creattionTime;
	private int per_day_otp_limt;
	private String Status ;

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public int getPer_day_otp_limt() {
		return per_day_otp_limt;
	}

	public void setPer_day_otp_limt(int per_day_otp_limt) {
		this.per_day_otp_limt = per_day_otp_limt;
	}

	public LocalDateTime getCreattionTime() {
		return creattionTime;
	}

	public void setCreattionTime(LocalDateTime creattionTime) {
		this.creattionTime = creattionTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
