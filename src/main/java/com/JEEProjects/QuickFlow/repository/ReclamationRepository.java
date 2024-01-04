package com.JEEProjects.QuickFlow.repository;

import java.util.List;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.JEEProjects.QuickFlow.models.RECLAMATION;
import com.JEEProjects.QuickFlow.models.SERVICE;
import com.JEEProjects.QuickFlow.models.StatusReclmation;

public interface ReclamationRepository extends MongoRepository<RECLAMATION, String>  {
	
	List<RECLAMATION> findByService(SERVICE service);
	Optional<RECLAMATION> findById(String reclId);
	 Long countByStatus(StatusReclmation status);
	
		Optional<RECLAMATION> findById(Long reclId);
		List<RECLAMATION> findAll();
		@Query("SELECT DISTINCT r.status FROM RECLAMATION r")
		List<StatusReclmation> findDistinctStatus();

		List<RECLAMATION> findByStatus(StatusReclmation status);

	
	
	

}