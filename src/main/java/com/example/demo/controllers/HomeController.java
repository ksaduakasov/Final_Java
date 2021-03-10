package com.example.demo.controllers;

import com.example.demo.models.Answer;
import com.example.demo.models.Profile;
import com.example.demo.models.Question;
import com.example.demo.services.AnswerService;
import com.example.demo.services.ProfileService;
import com.example.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final QuestionService questionService;
    private final ProfileService profileService;

    @Autowired
    public HomeController(QuestionService questionService, ProfileService profileService) {
        this.questionService = questionService;
        this.profileService = profileService;
    }

    @GetMapping("/")
    public String homePage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("newAnswer", new Answer());
            model.addAttribute("questionIdforAnswer", new Question());
            model.addAttribute("questions", questionService.getAllNoAnsweredQuestions(authentication.getName()));

        } else {
            model.addAttribute("questions", questionService.getAllQuestions());

        }
        return "home";

    }

    @PostMapping("/")
    public String answer(@ModelAttribute("newAnswer") Answer ans){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Profile profile = (Profile) profileService.loadUserByUsername(username);
            profileService.vote(ans, profile);
        } else {
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String authorization(){
        return "login";
    }

    @GetMapping("/logout")
    public String logOut(){
        return "home";
    }


}
