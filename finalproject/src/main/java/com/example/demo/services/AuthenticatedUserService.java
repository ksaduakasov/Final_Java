package com.example.demo.services;

import com.example.demo.models.Profile;
import com.example.demo.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

    @Autowired
    private ProfileRepository profileRepository;

    public boolean hasId(int id){
        String username =  SecurityContextHolder.getContext().getAuthentication().getName();
        Profile user = profileRepository.findByUsername(username);
        return (user.getProfileId() == id);
    }

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
