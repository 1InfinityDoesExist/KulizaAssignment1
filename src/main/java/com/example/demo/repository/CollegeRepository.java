package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.beans.College;

@Repository
public interface CollegeRepository extends CrudRepository<College, Long>, JpaRepository<College, Long> {

	@Query("Select College from #{#entityName} College where id=?1 and isDeleted=false")
	public College getCollegeById(Long id);

	@Query("Select College from #{#entityName} College where isDeleted = false")
	public List<College> getAllCollege();

}
