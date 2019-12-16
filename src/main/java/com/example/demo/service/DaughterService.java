package com.example.demo.service;


import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Daughter;
import com.example.demo.repository.DaughterRepository;

@Service
public class DaughterService {

	private static final Logger logger = LoggerFactory.getLogger(DaughterService.class);

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
}
