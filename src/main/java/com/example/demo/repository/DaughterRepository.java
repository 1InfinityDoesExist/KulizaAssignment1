package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.beans.Daughter;

@Repository
public interface DaughterRepository extends JpaRepository<Daughter, Long>, CrudRepository<Daughter, Long> {

}
