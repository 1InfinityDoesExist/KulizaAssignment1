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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.College;
import com.example.demo.service.CollegeService;
import com.example.demo.service.MapStateToError;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api/object/college")
@Api(value = "College Crud Operations", description = "College Crud Operation ")
public class CollegeController {

	private static final Logger logger = LoggerFactory.getLogger(CollegeController.class);
	@Autowired
	private CollegeService collegeService;

	@Autowired
	private MapStateToError mapStateToError;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "College Crud Operation", notes = "This is just College Crud Operations", response = College.class)
	public ResponseEntity<?> createCollegeResource(@Valid @RequestBody College college, BindingResult bindingResult) {
		ResponseEntity<?> errorMap = mapStateToError.mapStateToError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		College collegeToDB = collegeService.saveCollege(college);
		Gson gson = new Gson();
		String gsonString = gson.toJson(collegeToDB);

		return new ResponseEntity<String>(gsonString, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = "applicaation/json")
	@ResponseBody
	@ApiOperation(value = "College Crud Operation", notes = "Just College Crud Operation", response = College.class)
	public ResponseEntity<?> getCollegeByID(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {

		College collegeFromDB = collegeService.getCollegeByID(id);
		if (collegeFromDB == null) {
			return new ResponseEntity<String>("Sorry Could Not Found Data", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(collegeFromDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retieve College Resource", notes = "Crud Repository", response = College.class, responseContainer = "LIST")
	public ResponseEntity<?> getCollege() {
		List<College> collegeList = collegeService.getAllCollegeDetials();
		if (collegeList.size() == 0 || collegeList == null) {
			return new ResponseEntity<String>("Could Not Found Any Data", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String stringGson = gson.toJson(collegeList);
		return new ResponseEntity<String>(stringGson, HttpStatus.OK);

	}

}
