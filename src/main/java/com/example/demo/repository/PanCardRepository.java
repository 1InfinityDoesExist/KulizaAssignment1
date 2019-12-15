package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.beans.PanCard;

@Repository
public interface PanCardRepository extends JpaRepository<PanCard, Long>, CrudRepository<PanCard, Long> {

	@Query("Select PanCard from #{#entityName} PanCard where id=?1 and deleteFlag = false")
	public PanCard getPanCardByID(Long id);

	@Query("Select PanCard from #{#entityName} PanCard where deleteFlag = false")
	public List<PanCard> getAllPanCardDetails();

	@Transactional
	@Modifying
	@Query("Update PanCard set deleteFlag = true where id=?1")
	public void deletePanCardById(Long id);

}
