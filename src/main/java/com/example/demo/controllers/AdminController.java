package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.services.GroupService;
import com.example.demo.services.InterestService;
import com.example.demo.services.ProfileService;
import com.example.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProfileService profileService;
    private final QuestionService questionService;
    private final GroupService groupService;
    private final InterestService interestService;

    @Autowired
    public AdminController(ProfileService profileService,
                           QuestionService questionService,
                           GroupService groupService,
                           InterestService interestService) {
        this.profileService = profileService;
        this.questionService = questionService;
        this.groupService = groupService;
        this.interestService = interestService;
    }

    @GetMapping
    public String admin(Model model) {

        model.addAttribute("questions", questionService.getAllQuestions());
        model.addAttribute("profiles", profileService.getAllProfiles());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("interests",interestService.getAllInterests());
        model.addAttribute("groupDTO", new Group());
        model.addAttribute("interestDTO", new Interest());
        return "admin";

    }

    @GetMapping("/question/add")
    public String getAddQuestionPage(Model model) {
        model.addAttribute("interests", interestService.getAllInterests());
        Question question = new Question();
        List<Answer> answerList = IntStream.range(0,6)
                .mapToObj(i ->{
                    return new Answer();
                })
                .collect(Collectors.toList());
        question.setAnswers(answerList);
        model.addAttribute("questionDTO", question);
        return "question";
    }

    @GetMapping("/question/edit/{id}")
    public String getEditQuestionPage(@PathVariable(value = "id") int id, Model model) {
        Question question = questionService.getQuestionById(id);
        model.addAttribute("questionToEdit", question);
        int count = question.getAnswers().size();
        return "questionedit";
    }

}
