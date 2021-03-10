package com.example.demo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private int answerId;

    @ManyToOne
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_answers_question"))
    private Question questionId;

    @Column(name = "answer")
    private String answer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "voting",
            joinColumns = @JoinColumn(name="answer_id", foreignKey = @ForeignKey(name = "fk_answer_profile")),
            inverseJoinColumns = @JoinColumn(name="profile_id", foreignKey = @ForeignKey(name = "fk_profile_answer"))
    )
    private List<Profile> participatedUsers = new ArrayList<>();

    @PreRemove
    private void removeUsersFromAnswers() {
        for (Profile a: participatedUsers) {
            a.getUserAnswers().removeAll(a.getUserAnswers());
        }
    }

    public Answer() {
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Profile> getParticipatedUsers() {
        return participatedUsers;
    }

    public void setParticipatedUsers(List<Profile> participatedUsers) {
        this.participatedUsers = participatedUsers;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "questionId=" + questionId +
                ", answer='" + answer + '\'' +
                '}';
    }
}
