package com.ravin.questionservice.controller;


import com.ravin.questionservice.model.Question;
import com.ravin.questionservice.model.QuestionWrapper;
import com.ravin.questionservice.model.QuizResponse;
import com.ravin.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("questions/{id}")
    public Question getQuestionById(@PathVariable Integer id){
        return questionService.getQuestionById(id);
    }

    @GetMapping("/category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/create")
    public String saveQuestion(@RequestBody Question question){
        return questionService.saveQuestion(question);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestion(id);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String category, @RequestParam Integer numQ
    ){
        return questionService.getQuestionsForQuiz(category, numQ);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromIds(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<String> getScore(@RequestBody List<QuizResponse> responses){
        return questionService.getScore(responses);
    }
}
