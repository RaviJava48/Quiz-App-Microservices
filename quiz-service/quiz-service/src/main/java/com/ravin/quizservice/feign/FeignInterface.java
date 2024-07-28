package com.ravin.quizservice.feign;

import com.ravin.quizservice.model.QuestionWrapper;
import com.ravin.quizservice.model.QuizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface FeignInterface {

    @GetMapping("question/generate")
    ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String category, @RequestParam Integer numQ
    );

    @PostMapping("question/getQuestions")
    ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> questionIds);

    @PostMapping("question/getScore")
    ResponseEntity<String> getScore(@RequestBody List<QuizResponse> responses);
}
