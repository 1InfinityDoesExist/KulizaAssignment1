package com.example.demo.exception;

public class AadharCardAlreadyExistResponse {
	private String aadharCard;

	public AadharCardAlreadyExistResponse(String aadharCard) {
		super();
		this.aadharCard = aadharCard;
	}

	public String getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
	}

}
