package com.JEEProjects.QuickFlow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.JEEProjects.QuickFlow.models.ARRONDISSEMENT;
import com.JEEProjects.QuickFlow.models.SERVICE;

@Repository
public interface ServicesRepository extends MongoRepository<SERVICE, String> {
   public  List<SERVICE> findAll();
  public   Optional<SERVICE> findById(String id);

}
