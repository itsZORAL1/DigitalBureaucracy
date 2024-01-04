package com.JEEProjects.QuickFlow.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.DEMANDE;

public interface DemandeRepository extends MongoRepository<DEMANDE, String> {
	void deleteByCitoyen(CITOYEN citoyen);
	List<DEMANDE> findByCitoyen(CITOYEN citoyen);

	List<DEMANDE> findAll();


}
