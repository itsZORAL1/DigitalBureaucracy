package com.JEEProjects.QuickFlow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JEEProjects.QuickFlow.models.Questions;
import com.JEEProjects.QuickFlow.service.QuestionsService;

@Controller
public class QuestionsController {
	
    private QuestionsService questions;

    @Autowired
    public QuestionsController(QuestionsService questions) {
        this.questions = questions;
    }

    @GetMapping("/questions")
    @ResponseBody
    public ResponseEntity<Questions> getQuestionByStep(@RequestParam int step) {
        List<Questions> questionsList = this.questions.AllQuestions();

        if (step >= 1 && step <= questionsList.size()) {
            return ResponseEntity.ok(questionsList.get(step - 1));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
