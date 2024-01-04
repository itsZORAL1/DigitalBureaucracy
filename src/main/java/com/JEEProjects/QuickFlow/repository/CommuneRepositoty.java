package com.JEEProjects.QuickFlow.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.JEEProjects.QuickFlow.models.COMMUNE;


@Repository
public interface CommuneRepositoty  extends MongoRepository<COMMUNE, String>{

}
