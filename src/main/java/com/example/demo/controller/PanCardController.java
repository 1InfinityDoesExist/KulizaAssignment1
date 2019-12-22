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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.PanCard;
import com.example.demo.service.MapStateToError;
import com.example.demo.service.PanCardService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api/object/pan")
@Api(value = "PanCard Crud Operations", description = "PanCard Crud Operation")
public class PanCardController {

	private static final Logger logger = LoggerFactory.getLogger(PanCardController.class);

	@Autowired
	private PanCardService panCardService;

	@Autowired
	private MapStateToError mapStateToError;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create PanCard Resource", notes = "Create Pan Resource", response = PanCard.class)
	public ResponseEntity<?> createPan(@Valid @RequestBody PanCard panCard, BindingResult bindingResult) {
		ResponseEntity<?> errorMap = mapStateToError.mapStateToError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		PanCard panCardToDB = panCardService.savePanCardDetails(panCard);
		Gson gson = new Gson();
		String panCardResponse = gson.toJson(panCardToDB);
		return new ResponseEntity<String>(panCardResponse, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve PanCard Resource", notes = "Retrieve Pan", response = PanCard.class)
	public ResponseEntity<?> getPanCardByID(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
		PanCard panCardToDB = panCardService.getPanCardById(id);
		if (panCardToDB == null) {
			return new ResponseEntity<String>("Sorry No Data Found For This ID:", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String stringGson = gson.toJson(panCardToDB);
		return new ResponseEntity<String>(stringGson, HttpStatus.OK);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve All PanCard Resource From DB", notes = "Retrieve All Pan Card Details", response = PanCard.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllPanCard() {
		List<PanCard> panCardList = panCardService.getAllPanCardDetails();
		if (panCardList.size() == 0 || panCardList == null) {
			return new ResponseEntity<String>("Sorry Not Data Found In The DB", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String response = gson.toJson(panCardList);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE, produces = "text/plain")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Remove Resource Of PanCard From The DB", notes = "Remove PanCard Resource", response = String.class)
	public ResponseEntity<?> deletePanById(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {

		ResponseEntity<?> responseEntity = getPanCardByID(id);
		String stringResponse = (String) responseEntity.getBody();
		if (stringResponse.equals("Sorry No Data Found For This ID:")) {
			return new ResponseEntity<String>("No Data Found For This ID:- " + id, HttpStatus.BAD_REQUEST);
		}
		String response = panCardService.deletePanCardByID(id);
		if (response == null) {
			return new ResponseEntity<String>("Sorry Could Not Found Data For This ID:", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(path = "/update/{id}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update PanCard Resource", notes = "Its A Partial Update", response = PanCard.class)
	public ResponseEntity<?> updatePanCardByID(@RequestBody String panCard, @PathVariable(value = "id") Long id)
			throws JsonParseException, JsonMappingException, IOException, ParseException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		PanCard updatedPanCard = panCardService.updatePanCard(panCard, id);
		if (updatedPanCard == null) {
			return new ResponseEntity<String>("Sorry Could Not Update The PanCard By ID" + id, HttpStatus.OK);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(updatedPanCard);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);

	}
}
