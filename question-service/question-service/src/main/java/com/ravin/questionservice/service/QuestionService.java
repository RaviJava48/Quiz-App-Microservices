package com.ravin.questionservice.service;

import com.ravin.questionservice.dao.QuestionDao;
import com.ravin.questionservice.model.Question;
import com.ravin.questionservice.model.QuestionWrapper;
import com.ravin.questionservice.model.QuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
    }

    public Question getQuestionById(Integer id) {
        return questionDao.findById(id).get();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public String saveQuestion(Question question) {
        questionDao.save(question);
        return "Success";
    }

    public String deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        return "Deleted successfully";
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ) {
        List<Integer> questionIds = questionDao.findRandomQuestionsByCategory(category, numQ);
        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        for(Integer id: questionIds){
            Question question = questionDao.findById(id).get();
            QuestionWrapper wrapper = QuestionWrapper.builder()
                    .id(question.getId())
                    .questionTitle(question.getQuestionTitle())
                    .option1(question.getOption1())
                    .option2(question.getOption2())
                    .option3(question.getOption3())
                    .option4(question.getOption4())
                    .build();
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<String> getScore(List<QuizResponse> responses) {
        int score = 0;
        for(QuizResponse response: responses){
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer()))
                score++;
        }
        return new ResponseEntity<>("Your score is "+score, HttpStatus.OK);
    }
}
