package com.JEEProjects.QuickFlow.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;
import com.JEEProjects.QuickFlow.models.CITOYEN;

@Repository
public interface ArrondissementRepository extends MongoRepository<ARRONDISSEMENT, String>{
	List<ARRONDISSEMENT> findAll();

}
