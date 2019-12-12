package com.example.demo.exception;

public class PanCardAlreadyExistResponse {

	private String panCardNumber;

	public PanCardAlreadyExistResponse(String panCardNumber) {
		super();
		this.panCardNumber = panCardNumber;
	}

	public String getPanCardNumber() {
		return panCardNumber;
	}

	public void setPanCardNumber(String panCardNumber) {
		this.panCardNumber = panCardNumber;
	}

}
