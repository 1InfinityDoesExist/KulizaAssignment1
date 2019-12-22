package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.AadharCard;
import com.example.demo.service.AadharCardService;
import com.example.demo.service.MapStateToError;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/object/aadharCard")
@Api(value = "AadharCard Operations", description = "AadharCard Operations")
public class AadharCardController {

	private static final Logger logger = LoggerFactory.getLogger(AadharCardController.class);

	@Autowired
	private AadharCardService aadharCardService;

	@Autowired
	private MapStateToError mapStateToError;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create AadharCard Resource", notes = "Its Just To Store AadharCard Resource In Database", response = AadharCard.class)
	public ResponseEntity<?> createAadharCardResource(@Valid @RequestBody AadharCard aadharCard,
			BindingResult bindingResult) {
		logger.info("************Begening of Creating Resource for AadharCard*************************");
		ResponseEntity<?> errorMap = mapStateToError.mapStateToError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		AadharCard aadharCardToDB = aadharCardService.saveAadharCardDetails(aadharCard);
		System.out.println(aadharCardToDB);

		Gson gson = new Gson();
		String gsonString = gson.toJson(aadharCardToDB);
		logger.info("************End of Creating AadharCard Resource***********************", aadharCardToDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.CREATED);

	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve All AadharCards Details", notes = "Retreive All AadharCard's Resource", response = AadharCard.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllAaadharCardDetails() {
		List<AadharCard> listOfAadharCard = aadharCardService.getAllAadhars();
		if (listOfAadharCard == null || listOfAadharCard.size() == 0) {
			return new ResponseEntity<String>("Sorry No Data Found", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(listOfAadharCard);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve AadharData By ID", notes = "Retrieve AadharCard Details By ID", response = AadharCard.class)
	public ResponseEntity<?> getAadharCardDetailsByID(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
		AadharCard aadharCard = aadharCardService.getAadharCardById(id);
		if (aadharCard == null) {
			return new ResponseEntity<String>("No Value Retrieved For This ID", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(gson);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE, produces = "text/plain")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Remove AadharCard Resource From DB", notes = "Delete AadharCard By Id", response = String.class)
	public ResponseEntity<?> deleteAadharCardById(@PathVariable(value = "id") Long id) {

		ResponseEntity<?> responseString = getAadharCardDetailsByID(id);
		String stringResponse = (String) responseString.getBody();
		if (stringResponse.equals("No Value Retrieved For This ID")) {
			return new ResponseEntity<String>("No Data Available For This ID:-" + id, HttpStatus.BAD_REQUEST);
		}

		AadharCard aadharCard = new Gson().fromJson(stringResponse, AadharCard.class);
		logger.info("Printing AadharCardDetails" + aadharCard);
		String response = aadharCardService.deleteAadharCardById(id);
		if (response == null) {
			return new ResponseEntity<String>("Sorry Remove The Resource", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(path = "/update/{id}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update AadharCard  Resource", notes = "Partial Update of AadharCard", response = AadharCard.class)
	public ResponseEntity<?> updateAadharCard(@RequestBody String aadharCard, @PathVariable(value = "id") Long id)
			throws JsonParseException, JsonMappingException, IOException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ParseException {

		AadharCard aadharCardFromDB = aadharCardService.updateAadharCard(aadharCard, id);
		if (aadharCardFromDB == null) {
			return new ResponseEntity<String>("Sorry Coudl Not Update The AadharCard Details", HttpStatus.BAD_REQUEST);
		}

		Gson gson = new Gson();
		String gsonString = gson.toJson(aadharCardFromDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.BAD_REQUEST);

	}
}
