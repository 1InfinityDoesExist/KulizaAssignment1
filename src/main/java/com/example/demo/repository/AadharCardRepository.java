package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.beans.AadharCard;

@Repository
public interface AadharCardRepository extends CrudRepository<AadharCard, Long>, JpaRepository<AadharCard, Long> {

	@Query("Select AadharCard from #{#entityName} AadharCard where isDeleted = false")
	public List<AadharCard> getAllAadhar();

	@Query("Select AadharCard from #{#entityName} AadharCard where id=?1 and isDeleted = false")
	public AadharCard getAadharCardById(Long id);

	@Transactional
	@Modifying
	@Query("Update AadharCard set isDeleted = true where id=?1")
	public void deleteAadharCardById(Long id);

}
