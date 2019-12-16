package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.College;
import com.example.demo.repository.CollegeRepository;

@RestController
@RequestMapping(path = "/api/object/college")
public class CollegeController {

	@Autowired
	private CollegeRepository collegeRepository;

	@PostMapping(path = "/create")
	public ResponseEntity<?> createCollegeResource(@Valid @RequestBody College college) {
		College collegeToDB = collegeRepository.save(college);
		return new ResponseEntity<College>(collegeToDB, HttpStatus.CREATED);
	}
}
