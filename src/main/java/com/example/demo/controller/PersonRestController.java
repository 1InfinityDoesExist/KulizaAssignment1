package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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

import com.example.demo.beans.Person;
import com.example.demo.service.MapStateToError;
import com.example.demo.service.PersonService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/api/object")
@Api(value = "Person Object Crud Operations", description = "Crud Operation Of Person Object")
public class PersonRestController {

	private static final Logger logger = LoggerFactory.getLogger(PersonRestController.class);
	@Autowired
	private PersonService personService;

	@Autowired
	private MapStateToError mapStateToError;

	@RequestMapping(path = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create Person Resource", notes = "Persist Person In The Database", response = Person.class)
	public ResponseEntity<?> createPerson(@Valid @RequestBody Person person, BindingResult bindingResult) {
		ResponseEntity<?> errorMap = mapStateToError.mapStateToError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		Person personToDB = personService.createPerson(person);
		Gson gson = new Gson();
		String stringResponse = gson.toJson(personToDB);
		return new ResponseEntity<String>(stringResponse, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "getSonByID", notes = "", response = Person.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Person Does't Exist") })
	public ResponseEntity<?> getPersonByID(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
		Person personFromDB = personService.getPersonById(id);
		if (personFromDB == null) {
			return new ResponseEntity<String>("Sorry No Data Exist For This ID", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String stringResponse = gson.toJson(personFromDB);
		return new ResponseEntity<String>(stringResponse, HttpStatus.OK);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve Person Resource From The DB", notes = "", response = Person.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllPerson() {
		List<Person> personFromDB = personService.getAllPerson();
		if (personFromDB == null || personFromDB.size() == 0) {
			return new ResponseEntity<String>("Sorry No Data Exist", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String stringResponse = gson.toJson(personFromDB);
		return new ResponseEntity<String>(stringResponse, HttpStatus.OK);
	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE, produces = "plain/text")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Remove Person Resource From DB Using ID", notes = "Id Is Mandatory", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SuccessFully Deleted"),
			@ApiResponse(code = 400, message = "In Case Son Resources Could Not Be Deleted") })
	public ResponseEntity<?> deletePersonByID(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") @NotBlank @NotEmpty Long id) {

		ResponseEntity<?> responseEntity = getPersonByID(id);
		String res = (String) responseEntity.getBody();
		if (res.equals("Sorry No Data Exist For This ID")) {
			return new ResponseEntity<String>("Sorry No Data Found For This ID", HttpStatus.BAD_REQUEST);
		}

		Person person = new Gson().fromJson(res, Person.class);
		logger.info("Person Details:-" + person);

		String response = personService.deletePersonByID(id);
		if (response == null) {
			return new ResponseEntity<String>("Sorry No Data Exist For This Id", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(path = "/update/{id}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update Person Resource", notes = "Its A Parital Udate", response = Person.class)
	public ResponseEntity<?> updatePerson(@RequestBody String person, @PathVariable(value = "id") Long id)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException,
			JsonParseException, JsonMappingException, IOException {

		Person personFromDB = personService.updatePerson(person, id);
		if (personFromDB == null) {
			return new ResponseEntity<String>("Sorry Could Not Update Person Resource From DB", HttpStatus.BAD_GATEWAY);
		}

		Gson gson = new Gson();
		String gsonString = gson.toJson(personFromDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

}
