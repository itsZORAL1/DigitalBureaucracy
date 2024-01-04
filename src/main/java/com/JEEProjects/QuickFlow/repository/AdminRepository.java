package com.JEEProjects.QuickFlow.repository;

import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;
import java.util.Optional;

import com.JEEProjects.QuickFlow.models.ADMIN;
import com.JEEProjects.QuickFlow.models.SERVICE;


public interface AdminRepository  extends MongoRepository<ADMIN, String>  {
	
    
         boolean existsById(String id);
          Optional<ADMIN> findById(String id) ;
      

}