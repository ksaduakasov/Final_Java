package com.example.demo.controllers;

import com.example.demo.models.Profile;
import com.example.demo.services.GroupService;
import com.example.demo.services.InterestService;
import com.example.demo.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class RegistrationController {

    private final ProfileService profileService;
    private final GroupService groupService;
    private final InterestService interestService;

    @Autowired
    public RegistrationController(ProfileService profileService, GroupService groupService, InterestService interestService) {
        this.profileService = profileService;
        this.groupService = groupService;
        this.interestService = interestService;
    }

    @GetMapping
    public String signUpPage(Model model, @RequestParam(required = false) String err) {
        model.addAttribute("profile", new Profile());
        model.addAttribute("errMessage", err);
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("interests", interestService.getAllInterests());
        return "registration";
    }

    @PostMapping("/add")
    public String registration(@ModelAttribute("profile") Profile profile) {
        if(profileService.signup(profile)) {
            return "redirect:/login";
        } else {
            return "redirect:/signup?err=1";
        }
    }

}
