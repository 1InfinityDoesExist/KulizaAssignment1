package com.example.demo.exception;

public class MobileNumberAlreadyExistResponse {
	private String phone;

	public MobileNumberAlreadyExistResponse(String phone) {
		super();
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
