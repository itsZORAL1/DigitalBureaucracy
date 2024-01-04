package com.JEEProjects.QuickFlow.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;
import com.JEEProjects.QuickFlow.models.CITOYEN;
import org.springframework.stereotype.Repository;

@Repository
public interface CitoyenRepository extends MongoRepository<CITOYEN, String> {
	boolean existsByEmail(String email);
	void deleteById(String id);
	 Optional<CITOYEN> findById(String id);
	 boolean existsById(String id);
	List<CITOYEN> findByPassword(String password);
	List<CITOYEN> findByArrondissement(ARRONDISSEMENT arrondissement);
	List<CITOYEN> findAll();


	
}
