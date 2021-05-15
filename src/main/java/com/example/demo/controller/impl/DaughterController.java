package com.example.demo.controller.impl;

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

import com.example.demo.beans.Daughter;
import com.example.demo.service.DaughterService;
import com.example.demo.service.MapStateToError;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api/object/daughter")
@Api(value = "/api/object/daughter", description = "Daughter Controller Class")
public class DaughterController {

	private static final Logger logger = LoggerFactory.getLogger(DaughterController.class);

	@Autowired
	private DaughterService daughterService;

	@Autowired
	private MapStateToError mapStateToError;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Daughter Crud Operations", notes = "Create Daughter Resource", response = Daughter.class)
	public ResponseEntity<?> createDaughter(@Valid @RequestBody Daughter daughter, BindingResult bindingResult) {
		ResponseEntity<?> errorMap = mapStateToError.mapStateToError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		Daughter daughterToDB = daughterService.saveDaughterDetails(daughter);
		Gson gson = new Gson();
		String gsonString = gson.toJson(daughterToDB);

		return new ResponseEntity<String>(gsonString, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getDaughterById(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {

		Daughter daughter = daughterService.getDaughterByID(id);
		if (daughter == null) {
			return new ResponseEntity<String>("No Data Available For This ID", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(daughter);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Retrieve Daughter", response = Daughter.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllDaughter() {
		List<Daughter> daughterList = daughterService.getAllDaughter();
		Gson gson = new Gson();
		String gsonString = gson.toJson(daughterList);

		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

	@RequestMapping(path = "/delete/{id} ", method = RequestMethod.DELETE, produces = "text/plain")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Remove Resource From The", notes = "RemoveResources From The DB", response = String.class)
	public ResponseEntity<?> deleteDaughterById(@PathVariable(value = "id") Long id) {

		ResponseEntity<?> response = getDaughterById(id);
		String responseString = (String) response.getBody();
		if (responseString.equals("No Data Available For This ID")) {
			return new ResponseEntity<String>("Sorry No Data Found For The ID:" + id, HttpStatus.BAD_REQUEST);
		}
		Daughter dg = new Gson().fromJson(responseString, Daughter.class);
		logger.info("Daughter Object: " + dg);
		String str = daughterService.deleteDaughter(id);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@RequestMapping(path = "/update/{id}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update Daughter Resource", notes = "Update Daughter Resource", response = Daughter.class)
	public ResponseEntity<?> updateDaughter(@RequestBody String daughter, @PathVariable(value = "id") Long id)
			throws JsonParseException, JsonMappingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, ParseException {
		logger.info("Inside the Daughter Update Method");
		Daughter updatedDaughter = daughterService.updateDaughterDetails(daughter, id);
		if (updatedDaughter == null) {
			logger.error("Inside if Condition of Dauther Ojbect ");
			return new ResponseEntity<String>("Sorry No Daughter Details Found", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(updatedDaughter);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

}
