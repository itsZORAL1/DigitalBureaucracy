package com.JEEProjects.QuickFlow.service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import com.JEEProjects.QuickFlow.models.Questions;
import com.JEEProjects.QuickFlow.repository.QuestionsRepository;
import com.JEEProjects.QuickFlow.service.QuestionsService;

@Service
public class QuestionsServiceImplementation implements QuestionsService  {
	private QuestionsRepository questions;
	@Autowired
	public QuestionsServiceImplementation(QuestionsRepository questions) {
		this.questions=questions;
	}

	@Override
	public List<Questions> AllQuestions() {
		
		return this.questions.findAll();
	}

}
