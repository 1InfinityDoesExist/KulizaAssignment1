package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.PanCard;
import com.example.demo.service.MapStateToError;
import com.example.demo.service.PanCardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api/object/pan")
@Api(value = "/api/object/pan", description = "PanCard Operation")
public class PanCardController {

	private static final Logger logger = LoggerFactory.getLogger(PanCardController.class);

	@Autowired
	private PanCardService panCardService;

	@Autowired
	private MapStateToError mapStateToError;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "/create", notes = "Create Pan Resource", response = PanCard.class)
	public ResponseEntity<?> createPan(@Valid @RequestBody PanCard panCard, BindingResult bindingResult) {
		ResponseEntity<?> errorMap = mapStateToError.mapStateToError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		PanCard panCardToDB = panCardService.savePanCardDetails(panCard);
		return new ResponseEntity<PanCard>(panCardToDB, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "/get", notes = "Retrieve Pan", produces = "applicaiton/json")
	public ResponseEntity<?> getPanCardByID(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
		PanCard panCardToDB = panCardService.getPanCardById(id);
		return new ResponseEntity<PanCard>(panCardToDB, HttpStatus.OK);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "/get", notes = "Retrieve All Pan Card Details", response = PanCard.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllPanCard() {
		List<PanCard> panCardList = panCardService.getAllPanCardDetails();
		if (panCardList.size() == 0 || panCardList == null) {
			return new ResponseEntity<String>("Sorry Not Data Found In The DB", HttpStatus.CREATED);
		}
		return new ResponseEntity<List<PanCard>>(panCardList, HttpStatus.OK);
	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "/delete", notes = "Remove PanCard Resource")
	public ResponseEntity<?> deletePanById(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
		String response = panCardService.deletePanCardByID(id);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
