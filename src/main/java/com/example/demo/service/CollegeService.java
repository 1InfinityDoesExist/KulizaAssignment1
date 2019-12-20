package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cloudsearchv2.model.BaseException;
import com.example.demo.beans.College;
import com.example.demo.repository.CollegeRepository;

@Service
public class CollegeService {

	private static final Logger logger = LoggerFactory.getLogger(CollegeService.class);

	@Autowired
	private CollegeRepository collegeRepo;

	public College saveCollege(College college) {
		try {

			College collegeToDB = collegeRepo.save(college);
			return collegeToDB;
		} catch (final BaseException ex) {
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
			throw new BaseException(ex.getMessage());
		}
	}

	public List<College> getAllCollegeDetials() {
		// TODO Auto-generated method stub
		try {
			List<College> collegeList = collegeRepo.getAllCollege();
			return collegeList;
		} catch (final RuntimeException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
}
