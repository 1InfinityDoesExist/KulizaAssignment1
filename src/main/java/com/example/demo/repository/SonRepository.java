package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.beans.Son;

@Repository
public interface SonRepository extends CrudRepository<Son, Long>, JpaRepository<Son, Long> {

	@Query("Select Son from #{#entityName} Son where id=?1 and deleteFlag = false")
	public Son getSonByID(Long id);

	@Query("Select Son from #{#entityName} Son where deleteFlag = false")
	public List<Son> getAllSonDetails();

	@Transactional
	@Modifying
	@Query("Update Son set deleteFlag = true where id=?1")
	public void deleteSonDetailsById(Long id);

}
