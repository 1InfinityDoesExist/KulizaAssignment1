package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.PanCard;
import com.example.demo.repository.PanCardRepository;

@Service
public class PanCardService {

	private static final Logger logger = LoggerFactory.getLogger(PanCardService.class);
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
}
