package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<InputForm, Integer> {

	Optional<InputForm> findById(int id);

	List<InputForm> findAll();
	
	Optional<InputForm> findFirstByOrderByIdDesc(); 

}