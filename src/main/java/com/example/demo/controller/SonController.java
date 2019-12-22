package com.example.demo.controller;

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

import com.example.demo.beans.Son;
import com.example.demo.service.MapStateToError;
import com.example.demo.service.SonService;
import com.google.gson.Gson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/object/son")
@Api(value = "Son Rest Conroller", description = "Crud Operation for Son")
public class SonController {

	private static final Logger logger = LoggerFactory.getLogger(SonController.class);
	@Autowired
	private SonService sonService;

	@Autowired
	private MapStateToError mapStateToError;

	@RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create Son Resource", notes = "Create Son Resource", response = Son.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "In Case Son Resources Couldn't Be Created") })
	public ResponseEntity<?> persistSonDetatils(@Valid @RequestBody Son son, BindingResult bindingResult) {
		ResponseEntity<?> errorMap = mapStateToError.mapStateToError(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}
		Son sonToDB = sonService.saveSonDetails(son);
		Gson gson = new Gson();
		String gsonString = gson.toJson(sonToDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retrieve Son Resource", notes = "Retreive Son Resources Using Id", response = Son.class)
	public ResponseEntity<?> getSonDetailsByID(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
		Son son = sonService.getSonDetailsById(id);
		if (son == null) {
			return new ResponseEntity<String>("No Data Found For This Id", HttpStatus.BAD_REQUEST);
		}
		Gson gson = new Gson();
		String gsonString = gson.toJson(son);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

	@RequestMapping(path = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "/get", notes = "Retreive All Son Resources", response = Son.class, responseContainer = "LIST")
	public ResponseEntity<?> getAllSonDetails() {
		List<Son> listOfSon = sonService.getAllSonDetails();
		if (listOfSon == null || listOfSon.size() == 0) {
			return new ResponseEntity<String>("Sorry No Data Found In The Database", HttpStatus.BAD_REQUEST);
		}

		Gson gson = new Gson();
		String gsonString = gson.toJson(listOfSon);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);
	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE, produces = "text/plain")
	@ResponseBody
	@ApiOperation(value = "/delete", notes = "Remove Son Resources Using Id", response = String.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> deleteSonById(
			@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
		ResponseEntity<?> responseEntity = getSonDetailsByID(id);
		String responseString = (String) responseEntity.getBody();

		if (responseString.equals("No Data Found For This Id")) {
			return new ResponseEntity<String>("Sorry Not Data Exist For This ID:" + id, HttpStatus.BAD_REQUEST);
		}
		Son son = new Gson().fromJson(responseString, Son.class);

		logger.info("Son :-", son);
		String response = sonService.deleteSonDetails(id);
		if (response == null) {
			return new ResponseEntity<String>("Sorry id:-" + id + " Could Not Be Deleted", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(path = "/update/{id}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ApiOperation(value = "/update/{id}", notes = "Partial Update of The Son", response = Son.class)
	public ResponseEntity<?> updaeSon(@RequestBody String son, @PathVariable(value = "id") Long id)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
		Son sonFromDB = sonService.updateSonByID(son, id);
		if (sonFromDB == null) {
			return new ResponseEntity<String>("Sorry No Son Found For This ID:" + id, HttpStatus.BAD_REQUEST);
		}

		Gson gson = new Gson();
		String gsonString = gson.toJson(sonFromDB);
		return new ResponseEntity<String>(gsonString, HttpStatus.OK);

	}
}
