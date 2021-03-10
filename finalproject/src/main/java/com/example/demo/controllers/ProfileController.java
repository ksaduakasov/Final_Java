package com.example.demo.controllers;

import com.example.demo.models.Answer;
import com.example.demo.models.Profile;
import com.example.demo.services.AuthenticatedUserService;
import com.example.demo.services.GroupService;
import com.example.demo.services.ProfileService;
import com.example.demo.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final GroupService groupService;
    private final QuestionService questionService;
    private final AuthenticatedUserService authenticatedUserService;

    @Autowired
    public ProfileController(ProfileService profileService, GroupService groupService, QuestionService questionService, AuthenticatedUserService authenticatedUserService) {
        this.profileService = profileService;
        this.groupService = groupService;
        this.questionService = questionService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("{username}")
    public String getProfilePage(@PathVariable(value = "username") String username, Model model) {
        Profile profile = profileService.getProfileByUsername(username);
        for (Answer answer: profile.getUserAnswers()) {
            answer.getQuestionId().setCountAnswers(questionService.getNumberOfAnswersByQuestion(answer.getQuestionId()));
        }
        model.addAttribute("profile", profile);
        return "profile";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("@authenticatedUserService.hasId(#id) or hasRole('ROLE_ADMIN')")
    public String getProfileEditPage(@PathVariable(value = "id") int id, Model model) {
        model.addAttribute("profile", profileService.getProfile(id));
        model.addAttribute("newProfile", new Profile());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("roles", profileService.getAllRoles());
        return "profileedit";
    }

    @PostMapping("/edit/{id}")
    public String updateProfile(@PathVariable(value = "id") int id, @ModelAttribute("newProfile") Profile profile) {
        profileService.updateProfile(id, profile);

        return "redirect:/profile/"+authenticatedUserService.getUsername();
    }

    @GetMapping("/deleteVote/{id}")
    public String deleteVote(@PathVariable(value = "id") int id) {
        Profile profile = profileService.getProfileByUsername(authenticatedUserService.getUsername());
        List<Answer> answerList = new ArrayList<>(profile.getUserAnswers());
        for (Answer answer: answerList) {
            if (answer.getAnswerId() == id) {
                profile.getUserAnswers().remove(answer);
            }
        }
        profileService.updateProfile(profile.getProfileId(), profile);
        return "redirect:/profile/"+authenticatedUserService.getUsername();
    }

}
