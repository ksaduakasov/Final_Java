package com.example.demo.services.handlers;

import com.example.demo.logger.LogWriter;
import com.example.demo.models.Answer;
import com.example.demo.models.Profile;
import com.example.demo.repositories.ProfileRepository;

import java.io.File;

public class VoteHandler extends Thread {

    private Answer answer;
    private ProfileRepository profileRepository;
    private Profile profile;
    private final File file = new File("src/main/resources/logs/login.log");;

    public VoteHandler(Answer answer, Profile profile, ProfileRepository profileRepository) {
        this.answer = answer;
        this.profileRepository = profileRepository;
        this.profile = profile;
        this.start();
    }

    @Override
    public void run() {
        profileRepository.save(profile);
        String logMsg = '['+profile.getUsername() + " has answered " + answer.getAnswer() + " on question " + answer.getQuestionId().getQuestionName()+']';
        LogWriter.writeLog(file, logMsg);
    }
}
