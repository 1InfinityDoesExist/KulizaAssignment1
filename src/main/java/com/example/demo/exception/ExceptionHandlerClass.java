package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<?> panCardHandler(PanCardAlreadyExist ex, WebRequest webRequest) {
		PanCardAlreadyExistResponse response = new PanCardAlreadyExistResponse(ex.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<?> aadharCardHandler(AadharCardAlreadyExistException ex, WebRequest webRequest) {
		AadharCardAlreadyExistResponse response = new AadharCardAlreadyExistResponse(ex.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<?> mobileNumberHandler(MobileNumberAlreadyExist ex, WebRequest webRequest) {
		MobileNumberAlreadyExistResponse response = new MobileNumberAlreadyExistResponse(ex.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<?> emailIdExceptionHandler(EmailAlreadyExistException ex, WebRequest webRequest) {
		EmailAlreadyExistResponse response = new EmailAlreadyExistResponse(ex.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}
}
