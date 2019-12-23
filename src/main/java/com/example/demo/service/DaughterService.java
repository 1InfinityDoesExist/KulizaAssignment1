package com.example.demo.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.BasicDetails;
import com.example.demo.beans.Daughter;
import com.example.demo.exception.BaseException;
import com.example.demo.repository.DaughterRepository;
import com.example.demo.utility.ReflectionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DaughterService {

	private static final Logger logger = LoggerFactory.getLogger(DaughterService.class);

	ReflectionUtil refUtil = ReflectionUtil.getInstance();

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private DaughterRepository daughterRepo;

	public Daughter saveDaughterDetails(Daughter daughter) {
		try {
			Daughter daughterToDB = daughterRepo.save(daughter);
			return daughterToDB;
		} catch (final Exception ex) {
			logger.error("**********Could Not Create Resource For Daughter****************");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public Daughter getDaughterByID(Long id) {
		try {
			Daughter daughter = daughterRepo.getDaughterById(id);
			return daughter;
		} catch (final Exception ex) {
			logger.error("**********Could Not Retrieve Daughter Resource By ID***********");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public List<Daughter> getAllDaughter() {
		try {
			List<Daughter> daughterList = daughterRepo.getAllDaughter();
			return daughterList;
		} catch (final Exception ex) {
			logger.error("*****************Could Not Retrieve Daughter Details**************");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public String deleteDaughter(Long id) {
		try {
			daughterRepo.deleteDaughterByID(id);
			return "Successfully Deleted";
		} catch (final Exception ex) {
			logger.error("********Could Not Delete Daoughte By ID***************");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public Daughter updateDaughterDetails(String daughter, Long id) throws JsonParseException, JsonMappingException,
			IOException, ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		Daughter daughterFromDB = getDaughterByID(id);
		if (daughterFromDB == null) {
			throw new BaseException("Could Not Find Daughter Detial with ID :-" + id);
		}

		JSONParser parser = new JSONParser();
		Daughter daughterMapper = objectMapper.readValue(daughter, Daughter.class);
		logger.info("Daughter Payload String To Object :------> " + daughterMapper);

		try {
			JSONObject obj = (JSONObject) parser.parse(daughter);
			for (Iterator iterator = ((Map<String, String>) obj).keySet().iterator(); iterator.hasNext();) {
				String propName = (String) iterator.next();
				if (propName.equals("address")) {
					if (obj.get("address") != null) {
						JSONObject addObject = (JSONObject) obj.get("addres");
						if (daughterFromDB.getAddress() == null) {
							daughterFromDB.setAddress(null);
						}
						for (Object src : addObject.keySet()) {
							String addPropName = (String) src;
							refUtil.getSetterMethod("Address", addPropName).invoke(daughterFromDB.getAddress(),
									addObject.get(addPropName));
						}
					} else {
						daughterFromDB.setAddress(null);
					}
				} else if (propName.equals("basicDetails")) {
					if (obj.get("basicDetails") != null) {
						JSONObject basicObject = (JSONObject) obj.get("basicDetails");
						if (daughterFromDB.getBasicDetails() == null) {
							daughterFromDB.setBasicDetails(new BasicDetails());
						}
						for (Object basicObj : basicObject.keySet()) {
							String basicPropName = (String) basicObj;
							refUtil.getSetterMethod("BasicDetails", basicPropName)
									.invoke(daughterFromDB.getBasicDetails(), basicObject.get(basicPropName));
						}
					} else {
						daughterFromDB.setBasicDetails(null);
					}
				} else {
					refUtil.getSetterMethod("Daughter", propName).invoke(daughterFromDB, obj.get(propName));
				}

			}
		} catch (final BaseException ex) {
			logger.error(ex.getMessage());
		}

		Daughter updatedDaughter = daughterRepo.save(daughterFromDB);
		return updatedDaughter;

	}
}
