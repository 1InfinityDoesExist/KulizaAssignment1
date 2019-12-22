package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.AadharCard;
import com.example.demo.exception.AadharCardAlreadyExistException;
import com.example.demo.repository.AadharCardRepository;

@Service
public class AadharCardService {

	private static final Logger logger = LoggerFactory.getLogger(AadharCardService.class);

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

	public AadharCard updateAadharCard(String aadharCard, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
