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

import com.example.demo.beans.Daughter;
import com.example.demo.service.DaughterService;
import com.example.demo.service.MapStateToError;

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
	@ApiOperation(value = "/create", notes = "Create Daughter Resource", response = Daughter.class)
	public ResponseEntity<?> createDaughter(@Valid @RequestBody Daughter daughter, BindingResult bindingResult) {
		ResponseEntity<?> errorMap = mapStateToError.mapStateToError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		Daughter daughterToDB = daughterService.saveDaughterDetails(daughter);
		return new ResponseEntity<Daughter>(daughterToDB, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getDaughterById(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {

		Daughter daughter = daughterService.getDaughterByID(id);
		return new ResponseEntity<Daughter>(daughter, HttpStatus.OK);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "/get", notes = "Retrieve Daughter", response = Daughter.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllDaughter() {
		List<Daughter> daughterList = daughterService.getAllDaughter();
		return new ResponseEntity<List<Daughter>>(daughterList, HttpStatus.OK);
	}

	@RequestMapping(path = "/delete/{id} ", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDaughterById(@PathVariable(value = "id") Long id) {
		String response = daughterService.deleteDaughter(id);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
