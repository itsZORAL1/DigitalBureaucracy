package com.JEEProjects.QuickFlow.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.JEEProjects.QuickFlow.models.CITOYEN;
import com.JEEProjects.QuickFlow.models.CRACTERISTIQUE_FACIAL;

@Repository
public interface FaceDetectionRepository extends MongoRepository<CRACTERISTIQUE_FACIAL, String>{
	CRACTERISTIQUE_FACIAL findByCitoyenId(String id);
	void deleteByCitoyen(CITOYEN citoyen);

}
