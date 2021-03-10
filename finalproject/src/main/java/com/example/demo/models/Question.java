package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @Column(name = "question_body")
    private String questionName;

    @ManyToMany
    @JoinTable(
            name = "question_interest",
            joinColumns = @JoinColumn(name="question_id", foreignKey = @ForeignKey(name = "fk_question_interest")),
            inverseJoinColumns = @JoinColumn(name="interest_id", foreignKey = @ForeignKey(name = "fk_interest_question"))
    )
    private List<Interest> questionInterests = new ArrayList<>();

    @OneToMany(mappedBy = "questionId", orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @Column(name = "expire_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date expireDate;

    @Transient
    private int countAnswers;

    @PreRemove
    private void removeAnswersFromQuestion() {
        answers = new ArrayList<>();
    }

    public Question() {
    }

    public int getCountAnswers() {
        return countAnswers;
    }

    public void setCountAnswers(int countAnswers) {
        this.countAnswers = countAnswers;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String question) {
        this.questionName = question;
    }

    public List<Interest> getQuestionInterests() {
        return questionInterests;
    }

    public void setQuestionInterests(List<Interest> questionInterests) {
        this.questionInterests = questionInterests;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
