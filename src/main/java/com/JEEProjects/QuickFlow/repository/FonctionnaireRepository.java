package com.JEEProjects.QuickFlow.repository;



import com.JEEProjects.QuickFlow.models.FONCTIONNAIRE;
import java.util.List;
import java.util.Optional;

import com.JEEProjects.QuickFlow.models.SERVICE;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FonctionnaireRepository extends MongoRepository<FONCTIONNAIRE, String> {
    List<FONCTIONNAIRE> findByService(SERVICE service);
    boolean existsById(String id);
    Optional<FONCTIONNAIRE> findById(String id);
 



}

