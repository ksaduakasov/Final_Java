package com.example.demo.services;

import com.example.demo.models.Answer;
import com.example.demo.models.Profile;
import com.example.demo.models.Role;
import com.example.demo.repositories.AnswerRepository;
import com.example.demo.repositories.ProfileRepository;
import com.example.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService implements UserDetailsService {

    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, RoleRepository roleRepository, AnswerRepository answerRepository) {
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
        this.answerRepository = answerRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Profile getProfile(int id) {
        return profileRepository.getOne(id);
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public boolean signup(Profile profile) {
        Profile profileFromDb = profileRepository.findByUsername(profile.getUsername());
        if(profileFromDb == null){
            Role role = new Role();
            role.setRoleId(2);
            List<Role> roleList = new ArrayList<>();
            roleList.add(role);
            profile.setRoles(roleList);
            profile.setPassword(new BCryptPasswordEncoder(12).encode(profile.getPassword()));
            profileRepository.save(profile);
            return true;
        } else {
            return false;
        }
    }

    public void updateProfile(int id, Profile profile) {
        Profile updatedUser = profileRepository.findById(id).get();
        updatedUser.setFirstName(profile.getFirstName());
        updatedUser.setLastName(profile.getLastName());
        updatedUser.setAge(profile.getAge());
        updatedUser.setGroupId(profile.getGroupId());
        updatedUser.setGender(profile.getGender());
        if (!profile.getRoles().isEmpty()) {
            updatedUser.setRoles(profile.getRoles());
        }
        profileRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByUsername(username);
        if (profile == null) {
            throw new UsernameNotFoundException("Account with such username doesn't exit");
        }
        return profile;
    }

    @Transactional
    public void vote(Answer ans, Profile profile) {
        System.out.println(ans.getAnswer()+" "+ans.getQuestionId().getQuestionId());
        Answer answer = answerRepository.findByAnswerAndQuestionId_QuestionId(ans.getAnswer(), ans.getQuestionId().getQuestionId());
        System.out.println(answer);
        profile.getUserAnswers().add(answer);
        profileRepository.save(profile);
    }

    public Profile getProfileByUsername(String username) {
        return profileRepository.findByUsername(username);
    }
}
