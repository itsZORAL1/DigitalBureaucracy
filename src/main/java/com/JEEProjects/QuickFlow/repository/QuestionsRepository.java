package com.JEEProjects.QuickFlow.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.JEEProjects.QuickFlow.models.Questions;
@Repository
public interface QuestionsRepository extends MongoRepository<Questions,String> {

}
