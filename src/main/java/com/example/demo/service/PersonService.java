package com.example.demo.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Address;
import com.example.demo.beans.BasicDetails;
import com.example.demo.beans.Person;
import com.example.demo.exception.AadharCardAlreadyExistException;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.EmailAlreadyExistException;
import com.example.demo.exception.MobileNumberAlreadyExist;
import com.example.demo.exception.PanCardAlreadyExist;
import com.example.demo.repository.PersonRepository;
import com.example.demo.utility.ReflectionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class PersonService {

	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
	@Autowired
	private PersonRepository personRepository;

	ReflectionUtil refUtil = ReflectionUtil.getInstance();

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	public Person createPerson(Person person) {
		logger.info("*****************Start of CreatePerson of PersonService Class***********************");
		try {
			Person personToDB = personRepository.save(person);
			return personToDB;
		} catch (final PanCardAlreadyExist ex) {
			throw new PanCardAlreadyExist("Sorry PanCard Already Exist In The Database, Duplicate Case");
		} catch (final AadharCardAlreadyExistException ex) {
			throw new AadharCardAlreadyExistException("Sorry PanCard Already Exist In The Database, Duplicate Case");
		} catch (final MobileNumberAlreadyExist ex) {
			throw new MobileNumberAlreadyExist("Duplicate Mobile Number Exception");
		} catch (final EmailAlreadyExistException ex) {
			throw new EmailAlreadyExistException("Duplicate Email Exception");
		} finally {
			logger.info("*****************End of CreatePerson of PersonService Class***********************");
		}
	}

	public Person getPersonById(Long id) {
		try {
			Person personFromDB = personRepository.getPersonById(id);
			return personFromDB;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("Could Not Fetch Data From The Database");
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<Person> getAllPerson() {
		try {
			List<Person> listOfPersonFromDB = personRepository.getAllPerson();
			return listOfPersonFromDB;
		} catch (final Exception ex) {
			logger.info("Could Not Fetch Data From The Database");
			throw new RuntimeException(ex.getMessage());
		} finally {
			logger.info("End of getAllPerson Method");
		}
	}

	public String deletePersonByID(Long id) {
		try {
			String response = personRepository.deleteByID(id);
			return "SuccessFully Deleted";
		} catch (final Exception ex) {
			logger.info("Could Not Delete Data From The Database");
			throw new RuntimeException(ex.getMessage());
		} finally {
			logger.info("End of deletePersonByID");
		}
	}

	public Person updatePerson(String person, Long id)
			throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		Person personFromDB = getPersonById(id);
		if (personFromDB == null) {
			throw new BaseException("Sorry No Data Exist For This Id");
		}

		JSONParser parser = new JSONParser();

		Person personForDOB = objectMapper.readValue(person, Person.class);
		Date dob = personForDOB.getDob();
		try {
			JSONObject obj = (JSONObject) parser.parse(person);
			for (Iterator iterator = ((Map<String, String>) obj).keySet().iterator(); iterator.hasNext();) {
				String propName = (String) iterator.next();
				if (propName.equals("address")) {
					if (obj.get("address") != null) {
						JSONObject addObj = (JSONObject) obj.get("address");
						if (personFromDB.getAddress() == null) {
							personFromDB.setAddress(new Address());
						}

						for (Object src : addObj.keySet()) {
							String addProp = (String) src;
							refUtil.getSetterMethod("Address", addProp).invoke(personFromDB.getAddress(),
									addObj.get(addProp));
						}
					} else {
						personFromDB.setAddress(null);
					}
				} else if (propName.equals("basicDetials")) {
					if (obj.get("basicDetials") != null) {
						JSONObject basicObj = (JSONObject) obj.get("basicDetials");
						if (personFromDB.getBasicDetials() == null) {
							personFromDB.setBasicDetials(new BasicDetails());
						}

						for (Object src : basicObj.keySet()) {
							String str = (String) src;
							refUtil.getSetterMethod("BasicDetials", str).invoke(personFromDB.getBasicDetials(),
									basicObj.get(str));
						}
					}
				} else if (propName.equals("dob")) {
					personFromDB.setDob(dob);
				}

				else {
					refUtil.getSetterMethod("Person", propName).invoke(personFromDB, obj.get(propName));
				}
			}

		} catch (final BaseException ex) {
			logger.error(ex.getMessage());
		}

		Person updatedPerson = personRepository.save(personFromDB);
		return updatedPerson == null ? null : updatedPerson;

	}
}
