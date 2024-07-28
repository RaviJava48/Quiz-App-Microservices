package com.ravin.quizservice.controller;

import com.ravin.quizservice.model.QuestionWrapper;
import com.ravin.quizservice.model.QuizResponse;
import com.ravin.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(
            @RequestParam String category,
            @RequestParam Integer numQ,
            @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(@PathVariable Integer id){
        return quizService.getQuestionsById(id);
    }

    @GetMapping("/submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable Integer id, @RequestBody List<QuizResponse> quizResponses){
        return quizService.submitQuiz(id, quizResponses);
    }
}
