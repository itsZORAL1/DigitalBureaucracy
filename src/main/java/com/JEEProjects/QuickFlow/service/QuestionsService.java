package com.JEEProjects.QuickFlow.service;


import org.springframework.stereotype.Service;
import com.JEEProjects.QuickFlow.models.Questions;
import java.util.List;
@Service
public interface QuestionsService {
public List<Questions> AllQuestions();
}
