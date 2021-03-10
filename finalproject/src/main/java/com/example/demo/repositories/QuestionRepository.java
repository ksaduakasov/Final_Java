package com.example.demo.repositories;

import com.example.demo.models.Answer;
import com.example.demo.models.Interest;
import com.example.demo.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> getAllByQuestionInterestsContains(Interest interest);
}
