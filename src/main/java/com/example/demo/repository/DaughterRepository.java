package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.beans.Daughter;

@Repository
public interface DaughterRepository extends JpaRepository<Daughter, Long>, CrudRepository<Daughter, Long> {

	@Query("Select Daughter from #{#entityName} Daughter where id=?1 and deleteFlag = false")
	public Daughter getDaughterById(Long id);

	@Query("Select Daughter from #{#entityName} Daughter where deleteFlag = false")
	public List<Daughter> getAllDaughter();

	@Transactional
	@Modifying
	@Query("Update Daughter set deleteFlag = true where id=?1")
	public void deleteDaughterByID(Long id);

}
