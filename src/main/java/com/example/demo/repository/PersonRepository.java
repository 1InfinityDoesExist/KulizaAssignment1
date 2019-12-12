package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.beans.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, CrudRepository<Person, Long> {

	@Query("Select Person from #{#entityName} Person where id=?1 and deleteFlag = false")
	public Person getPersonById(Long id);

	@Query("Select Person from #{#entityName} Person where deleteFlag = false")
	public List<Person> getAllPerson();

	@Transactional
	@Modifying
	@Query("Update Person set deleteFlag = true where id=?1")
	public String deleteByID(Long id);

}
