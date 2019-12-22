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

import com.example.demo.beans.AadharCard;
import com.example.demo.beans.Address;
import com.example.demo.exception.AadharCardAlreadyExistException;
import com.example.demo.exception.BaseException;
import com.example.demo.repository.AadharCardRepository;
import com.example.demo.utility.ReflectionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class AadharCardService {

	private static final Logger logger = LoggerFactory.getLogger(AadharCardService.class);

	ReflectionUtil refUtil = ReflectionUtil.getInstance();

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Autowired
	private AadharCardRepository aadharCardRepository;

	public AadharCard saveAadharCardDetails(AadharCard aadharCard) {
		try {
			AadharCard aadharCardToDB = aadharCardRepository.save(aadharCard);
			return aadharCardToDB;
		} catch (final AadharCardAlreadyExistException ex) {
			logger.error(
					"*******************Could Not Persist AaadharCard Details to DB***********************************");
			throw new AadharCardAlreadyExistException("Duplicate AadharCard Violation");
		}
	}

	public List<AadharCard> getAllAadhars() {
		// TODO Auto-generated method stub
		try {
			List<AadharCard> responseAadharCard = aadharCardRepository.getAllAadhar();
			return responseAadharCard;
		} catch (final Exception ex) {
			logger.error("*********************Could Not Retrieve Data From DB**************************");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public AadharCard getAadharCardById(Long id) {
		try {
			AadharCard aadharCard = aadharCardRepository.getAadharCardById(id);
			return aadharCard;
		} catch (final Exception ex) {
			logger.error("**********Could Not Retrieve AadharCardBy ID: ***************");
			throw new RuntimeException(ex.getMessage());
		}
	}

	public String deleteAadharCardById(Long id) {
		try {
			aadharCardRepository.deleteAadharCardById(id);
			return "SuccessFully Deleted";
		} catch (final Exception ex) {
			logger.error("***********Could Not Delete AadharCardDetials By Id*************");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public AadharCard updateAadharCard(String aadharCard, Long id) throws JsonParseException, JsonMappingException,
			IOException, ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		AadharCard aadharCardFromDB = getAadharCardById(id);
		if (aadharCardFromDB == null) {
			throw new BaseException("Sorry No Data FoundFor This ID:" + id);
		}

		JSONParser parser = new JSONParser();
		AadharCard aadharCardFromPayload = objectMapper.readValue(aadharCard, AadharCard.class);
		Date dob = aadharCardFromPayload.getDob();

		try {
			JSONObject jsonObject = (JSONObject) parser.parse(aadharCard);
			for (Iterator iterator = ((Map<String, String>) jsonObject).keySet().iterator(); iterator.hasNext();) {
				String propName = (String) iterator.next();

				if (propName.equals("address")) {
					if (jsonObject.get("address") != null) {
						JSONObject addObject = (JSONObject) jsonObject.get("address");
						if (aadharCardFromDB.getAddress() == null) {
							aadharCardFromDB.setAddress(new Address());
						}
						for (Object obj : addObject.keySet()) {
							String addPropName = (String) obj;
							refUtil.getSetterMethod("Address", addPropName).invoke(aadharCardFromDB.getAddress(),
									addObject.get(addPropName));
						}
					} else {
						aadharCardFromDB.setAddress(null);
					}
				} else if (propName.equals("dob")) {
					aadharCardFromDB.setDob(dob);
				} else {
					refUtil.getSetterMethod("AadharCard", propName).invoke(aadharCardFromDB, jsonObject.get(propName));
				}
			}
		} catch (final BaseException ex) {
			logger.error(ex.getMessage());
		}
		AadharCard updatedAadharCard = aadharCardRepository.save(aadharCardFromDB);
		return updatedAadharCard;

	}

}
