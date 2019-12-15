package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.AadharCard;
import com.example.demo.service.AadharCardService;
import com.example.demo.service.MapStateToError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api/object/aadharCard")
@Api(value = "/api/object/aadharCard", description = "aadharCard Operations")
public class AadharCardController {

	@Autowired
	private AadharCardService aadharCardService;

	@Autowired
	private MapStateToError mapStateToError;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "/create", notes = "Create AadharCard Resource", response = AadharCard.class)
	public ResponseEntity<?> createAadharCardResource(@Valid @RequestBody AadharCard aadharCard,
			BindingResult bindingResult) {
		ResponseEntity<?> errorMap = mapStateToError.mapStateToError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		AadharCard aadharCardToDB = aadharCardService.saveAadharCardDetails(aadharCard);
		return new ResponseEntity<AadharCard>(aadharCardToDB, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "/get", notes = "Retreive All AadharCard's Resource", response = AadharCard.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllAaadharCardDetails() {
		List<AadharCard> listOfAadharCard = aadharCardService.getAllAadhars();
		if (listOfAadharCard == null || listOfAadharCard.size() == 0) {
			return new ResponseEntity<String>("Sorry No Data Found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<AadharCard>>(listOfAadharCard, HttpStatus.OK);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "/get/", notes = "Retrieve AadharCard Details By ID", response = AadharCard.class)
	public ResponseEntity<?> getAadharCardDetailsByID(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
		AadharCard aadharCard = aadharCardService.getAadharCardById(id);
		if (aadharCard == null) {
			return new ResponseEntity<String>("No Value Retrieved For This ID", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AadharCard>(aadharCard, HttpStatus.OK);
	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "/delete", notes = "Delete AadharCard By Id", response = String.class)
	public ResponseEntity<?> deleteAadharCardById(@PathVariable(value = "id") Long id) {
		String response = aadharCardService.deleteAadharCardById(id);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
