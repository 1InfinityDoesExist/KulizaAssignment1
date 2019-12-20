package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.College;
import com.example.demo.exception.BaseException;
import com.example.demo.repository.CollegeRepository;

@Service
public class CollegeService {

	private static final Logger logger = LoggerFactory.getLogger(CollegeService.class);

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
}
