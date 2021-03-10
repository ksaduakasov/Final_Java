package com.example.demo.services;

import com.example.demo.models.Answer;
import com.example.demo.models.Question;
import com.example.demo.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void addAnswers(List<Answer> answerList) {
        answerRepository.saveAll(answerList);
    }
}
