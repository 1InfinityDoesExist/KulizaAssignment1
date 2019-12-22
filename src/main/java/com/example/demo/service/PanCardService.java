package com.example.demo.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
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
import com.example.demo.beans.PanCard;
import com.example.demo.exception.BaseException;
import com.example.demo.repository.PanCardRepository;
import com.example.demo.utility.ReflectionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class PanCardService {

	private static final Logger logger = LoggerFactory.getLogger(PanCardService.class);

	ReflectionUtil refUtil = ReflectionUtil.getInstance();

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Autowired
	private PanCardRepository panCardRepository;

	public PanCard savePanCardDetails(PanCard panCard) {
		try {
			PanCard panCardToDB = panCardRepository.save(panCard);
			return panCardToDB;
		} catch (final Exception ex) {
			logger.error("****************Could Not Create PanCard Resource**********************");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public PanCard getPanCardById(Long id) {
		try {
			PanCard panCardToDb = panCardRepository.getPanCardByID(id);
			return panCardToDb;
		} catch (final Exception ex) {
			logger.error("****************Could Not Retreive PanCard Details*******************");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public List<PanCard> getAllPanCardDetails() {
		try {
			List<PanCard> listPanCard = panCardRepository.getAllPanCardDetails();
			return listPanCard;
		} catch (final Exception ex) {
			logger.error("********Could Not Retrieve Data From Database for panCard**************");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public String deletePanCardByID(Long id) {
		try {
			panCardRepository.deletePanCardById(id);
			return "SuccessFully Deleted";
		} catch (final Exception ex) {
			logger.error("**************Could Not Remove Resource of PanCard  From DB****************");
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public PanCard updatePanCard(String panCard, Long id) throws JsonParseException, JsonMappingException, IOException,
			ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		PanCard panCardFromDB = getPanCardById(id);
		if (panCardFromDB == null) {
			throw new BaseException("Sorry No Data Found For This ID:" + id);
		}
		JSONParser parser = new JSONParser();
		PanCard panCardFromPayload = objectMapper.readValue(panCard, PanCard.class);
		LocalDateTime validityFromPayload = panCardFromPayload.getValidity();
		Date dob = panCardFromPayload.getDob();

		try {
			JSONObject obj = (JSONObject) parser.parse(panCard);
			for (Iterator iterator = ((Map<String, String>) obj).keySet().iterator(); iterator.hasNext();) {
				String propName = (String) iterator.next();
				if (propName.equals("address")) {
					if (obj.get("address") != null) {
						JSONObject addObject = (JSONObject) obj.get("addres");
						if (panCardFromDB.getAddress() == null) {
							panCardFromDB.setAddress(new Address());
						}

						for (Object src : addObject.keySet()) {
							String addPropName = (String) src;
							refUtil.getSetterMethod("Address", addPropName).invoke(panCardFromDB.getAddress(),
									addObject.get(addPropName));
						}
					} else if (propName.equals("dob")) {
						panCardFromDB.setDob(dob);
					} else if (propName.equals("validity")) {
						panCardFromDB.setValidity(validityFromPayload);
					} else {
						panCardFromDB.setAddress(null);
					}

				} else {
					refUtil.getSetterMethod("PanCard", propName).invoke(panCardFromDB, obj.get(propName));
				}
			}
		} catch (final BaseException ex) {
			logger.error(ex.getMessage());
		}

		PanCard updatedPanCard = panCardRepository.save(panCardFromDB);
		return updatedPanCard;
	}
}
