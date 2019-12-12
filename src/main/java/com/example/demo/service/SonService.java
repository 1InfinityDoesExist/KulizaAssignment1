package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Son;
import com.example.demo.exception.EmailAlreadyExistException;
import com.example.demo.exception.MobileNumberAlreadyExist;
import com.example.demo.repository.SonRepository;

@Service
public class SonService {

	private static final Logger logger = LoggerFactory.getLogger(SonService.class);

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
			sonRepository.deleteSonDetailsById(id);
			return "SuccessFully Deleted";
		}catch(final Exception ex) {
			logger.error("Could not soft Delete Son Data");
			throw new RuntimeException(ex.getMessage());
		}finally {
			logger.info("End of deleteSonDetails");
		}
	}

}
