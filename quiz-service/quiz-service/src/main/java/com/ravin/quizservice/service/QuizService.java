package com.ravin.quizservice.service;

import com.ravin.quizservice.dao.QuizDao;
import com.ravin.quizservice.feign.FeignInterface;
import com.ravin.quizservice.model.QuestionWrapper;
import com.ravin.quizservice.model.Quiz;
import com.ravin.quizservice.model.QuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private FeignInterface feignInterface;

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        List<Integer> questions = feignInterface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz = Quiz.builder()
                .title(title)
                .questionIds(questions)
                .build();
        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        List<QuestionWrapper> questionWrappers = feignInterface.getQuestionsFromIds(questionIds).getBody();
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<String> submitQuiz(Integer id, List<QuizResponse> quizResponses) {
        String scoreMsg = feignInterface.getScore(quizResponses).getBody();
        return new ResponseEntity<>(scoreMsg, HttpStatus.OK);
    }
}
