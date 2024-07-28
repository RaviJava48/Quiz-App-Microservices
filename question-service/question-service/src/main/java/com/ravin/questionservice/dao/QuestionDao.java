package com.ravin.questionservice.dao;

import com.ravin.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    @Query("select q from Question q where category = ?1")
    List<Question> findByCategory(String category);

    @Query(value = "select id from question where category = ?1 order by rand() limit ?2", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, Integer numQ);
}
