package com.example.demo.controllers;

import com.example.demo.models.Answer;
import com.example.demo.models.Question;
import com.example.demo.services.AnswerService;
import com.example.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/question")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping("/add")
    public String addQuestion(@ModelAttribute("questionDTO") Question question) {
        question.getAnswers().forEach(System.out::println);
        question.getAnswers().removeIf(x -> x.getAnswer().equals(""));
        question.getAnswers().forEach(x -> x.setQuestionId(question));
        questionService.addQuestion(question);
        answerService.addAnswers(question.getAnswers());
        return "redirect:/admin";
    }

    @PostMapping("/update/{id}")
    public String addQuestion(@ModelAttribute("questionDTO") Question question, @PathVariable(value = "id") int id) {
        System.out.println(id);
        question.setQuestionId(id);
        question.getAnswers().forEach(x->x.setQuestionId(question));
        questionService.updateQuestion(question);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        questionService.delete(id);
        return "redirect:/admin";
    }
}
