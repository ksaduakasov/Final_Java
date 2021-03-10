package com.example.demo.repositories;

import com.example.demo.models.Answer;
import com.example.demo.models.Profile;
import com.example.demo.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findAllByQuestionId(Question questionId);
    Answer findByAnswerAndQuestionId_QuestionId(String answer, int question);
    List<Answer> findAnswersByParticipatedUsersContains(Profile profile);
}