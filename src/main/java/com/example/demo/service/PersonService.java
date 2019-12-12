package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Person;
import com.example.demo.exception.AadharCardAlreadyExistException;
import com.example.demo.exception.EmailAlreadyExistException;
import com.example.demo.exception.MobileNumberAlreadyExist;
import com.example.demo.exception.PanCardAlreadyExist;
import com.example.demo.repository.PersonRepository;

@Service
public class PersonService {

	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
	@Autowired
	private PersonRepository personRepository;

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
}
