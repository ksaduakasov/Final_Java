package com.example.demo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interests")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_id")
    private int interestId;
    @Column(name = "interest_name")
    private String interestName;
    @ManyToMany(mappedBy = "profileInterests")
    private List<Profile> profileSet = new ArrayList<>();
    @ManyToMany(mappedBy = "questionInterests")
    private List<Question> questionSet = new ArrayList<>();

    public Interest() {
    }

    public int getInterestId() {
        return interestId;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public List<Profile> getProfileSet() {
        return profileSet;
    }

    public void setProfileSet(List<Profile> profileSet) {
        this.profileSet = profileSet;
    }

    public List<Question> getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(List<Question> questionSet) {
        this.questionSet = questionSet;
    }
}
