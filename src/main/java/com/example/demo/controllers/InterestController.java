package com.example.demo.controllers;

import com.example.demo.models.Interest;
import com.example.demo.services.InterestService;
import com.example.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/interest")
public class InterestController {

    private final InterestService interestService;
    private final QuestionService questionService;

    @Autowired
    public InterestController(InterestService interestService, QuestionService questionService) {
        this.interestService = interestService;
        this.questionService = questionService;
    }

    @PostMapping("/add")
    public String addGroup(@ModelAttribute("interestDTO") Interest interest) {
        interestService.addInterest(interest);
        return "redirect:/admin";
    }

    @GetMapping("{id}")
    public String getInterestPage(@PathVariable(value = "id") int interestId, Model model) {
        model.addAttribute("questions", questionService.getQuestionsByInterest(interestService.getInterestById(interestId)));
        return "interest";
    }
}
