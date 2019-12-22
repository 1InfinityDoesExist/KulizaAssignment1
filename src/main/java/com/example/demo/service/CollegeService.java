package com.example.demo.service;

import java.lang.reflect.InvocationTargetException;
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

import com.example.demo.beans.College;
import com.example.demo.exception.BaseException;
import com.example.demo.repository.CollegeRepository;
import com.example.demo.utility.ReflectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class CollegeService {

	private static final Logger logger = LoggerFactory.getLogger(CollegeService.class);

	ReflectionUtil refUtil = ReflectionUtil.getInstance();

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Autowired
	private CollegeRepository collegeRepo;

	public College saveCollege(College college) {
		try {

			College collegeToDB = collegeRepo.save(college);
			if (collegeToDB == null) {
				throw new BaseException("Sorry Could Persist Data To The DB");
			}
			return collegeToDB;
		} catch (final BaseException ex) {
			logger.error("******************SaveData************************");
			throw new BaseException("Sorry Could Not Persist Data In DB");
		}
	}

	public College getCollegeByID(Long id) {
		try {
			College college = collegeRepo.getCollegeById(id);
			if (college == null) {
				throw new BaseException("Could Not Retrieve Data From DB");
			}
			return college;
		} catch (final BaseException ex) {
			logger.error("**************Inside getCollegeByID********************");
			throw new BaseException(ex.getMessage());
		}
	}

	public List<College> getAllCollegeDetials() {
		// TODO Auto-generated method stub
		try {
			List<College> collegeList = collegeRepo.getAllCollege();
			if (collegeList == null || collegeList.size() == 0) {
				throw new BaseException("Could Not Retrieve College Data From The DB");
			}
			return collegeList;
		} catch (final RuntimeException ex) {
			logger.error("****************Inside getAllCollegeDetails*******************");
			throw new RuntimeException(ex.getMessage());
		}
	}

	public String deleteCollegeByID(Long id) {
		// TODO Auto-generated method stub
		try {
			collegeRepo.deleteCollegeByID(id);
			return "Successfully Deleted";
		} catch (final Exception ex) {
			logger.error("*****************Inside DeleteCollege Method*********************");
			throw new RuntimeException(ex.getMessage());
		}
	}

	public College updateCollege(String college, Long id)
			throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		College collegeFromDB = getCollegeByID(id);
		if (collegeFromDB == null) {
			throw new BaseException("Sorry Could Not Found Any College Detais For This Id:" + id);
		}

		JSONParser parser = new JSONParser();
		try {

			JSONObject obj = (JSONObject) parser.parse(college);
			for (Iterator iterator = ((Map<String, String>) obj).keySet().iterator(); iterator.hasNext();) {
				String propName = (String) iterator.next();
				refUtil.getSetterMethod("College", propName).invoke(collegeFromDB, obj.get(propName));
			}
		} catch (final BaseException ex) {
			logger.error("Exception Caught:- " + ex.getMessage());
		}

		College updatedCollege = collegeRepo.save(collegeFromDB);
		return updatedCollege;
	}
}
