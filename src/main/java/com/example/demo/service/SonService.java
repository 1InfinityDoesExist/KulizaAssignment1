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

import com.example.demo.beans.Address;
import com.example.demo.beans.BasicDetails;
import com.example.demo.beans.Son;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.EmailAlreadyExistException;
import com.example.demo.exception.MobileNumberAlreadyExist;
import com.example.demo.repository.SonRepository;
import com.example.demo.utility.ReflectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class SonService {

	private static final Logger logger = LoggerFactory.getLogger(SonService.class);

	ReflectionUtil refUtil = ReflectionUtil.getInstance();

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Autowired
	private SonRepository sonRepository;

	public Son saveSonDetails(Son son) {
		// TODO Auto-generated method stub
		try {
			Son sonToDB = sonRepository.save(son);
			return sonToDB;
		} catch (final EmailAlreadyExistException ex) {
			throw new EmailAlreadyExistException("Sorry Email Id Already In User");
		} catch (final MobileNumberAlreadyExist ex) {
			throw new MobileNumberAlreadyExist("Sorry Mobile Number Already Exist");
		} finally {
			logger.info("End of SaveSonDetails of SonService Class");
		}
	}

	public Son getSonDetailsById(Long id) {
		try {
			Son sonFromDB = sonRepository.getSonByID(id);
			return sonFromDB;
		} catch (final Exception ex) {
			logger.error("Could Not Retreive Son Data By ID");
			throw new RuntimeException(ex.getMessage());
		} finally {
			logger.info("End Of The getSonDetailsByID");
		}
	}

	public List<Son> getAllSonDetails() {
		try {
			List<Son> listOfSons = sonRepository.getAllSonDetails();
			return listOfSons;
		} catch (final Exception ex) {
			logger.error("Could Not Retreive Data For All Sons");
			throw new RuntimeException(ex.getMessage());
		} finally {
			logger.info("End of GetAllSonDetatils Method");
		}
	}

	public String deleteSonDetails(Long id) {
		try {
			System.out.println("hello git testing");
			sonRepository.deleteSonDetailsById(id);
			return "SuccessFully Deleted";
		} catch (final Exception ex) {
			logger.error("Could not soft Delete Son Data");
			throw new RuntimeException(ex.getMessage());
		} finally {
			logger.info("End of deleteSonDetails");
		}

	}

	public Son updateSonByID(String son, Long id)
			throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		Son sonFromDB = getSonDetailsById(id);
		if (sonFromDB == null) {
			throw new BaseException("Sorry No DataFound For This ID");
		}

		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser.parse(son);
			for (Iterator iterator = ((Map<String, String>) obj).keySet().iterator(); iterator.hasNext();) {
				String propName = (String) iterator.next();
				if (propName.equals("address")) {
					if (obj.get("address") != null) {
						JSONObject addObj = (JSONObject) obj.get("address");
						if (sonFromDB.getAddress() == null) {
							sonFromDB.setAddress(new Address());
						}

						for (Object src : addObj.keySet()) {
							String addPropName = (String) src;
							refUtil.getSetterMethod("Address", addPropName).invoke(sonFromDB.getAddress(),
									addObj.get(addPropName));
						}
					} else {
						sonFromDB.setAddress(null);
					}
				} else if (propName.equals("basicDetails")) {
					if (obj.get("basicDetails") != null) {
						JSONObject basicObj = (JSONObject) obj.get("basicDetails");
						if (sonFromDB.getBasicDetails() == null) {
							sonFromDB.setBasicDetails(new BasicDetails());
						}
						for (Object src : basicObj.keySet()) {
							String basicPropName = (String) src;
							refUtil.getSetterMethod("BasicDetails", basicPropName).invoke(sonFromDB.getBasicDetails(),
									basicObj.get(basicPropName));
						}
					} else {
						sonFromDB.setBasicDetails(null);
					}
				}

				else {
					refUtil.getSetterMethod("Son", propName).invoke(sonFromDB, obj.get(propName));
				}
			}
		} catch (final BaseException ex) {
			logger.error(ex.getMessage());
		}

		Son sonToDB = sonRepository.save(sonFromDB);
		return sonToDB;
	}

}
