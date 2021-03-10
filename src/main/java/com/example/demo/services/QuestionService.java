package com.example.demo.services;

import com.example.demo.models.Answer;
import com.example.demo.models.Interest;
import com.example.demo.models.Profile;
import com.example.demo.models.Question;
import com.example.demo.repositories.AnswerRepository;
import com.example.demo.repositories.ProfileRepository;
import com.example.demo.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository, ProfileRepository profileRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.profileRepository = profileRepository;
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        for (Question question:questions) {
            question.setAnswers(answerRepository.findAllByQuestionId(question));
            question.setCountAnswers(getNumberOfAnswersByQuestion(question));
        }
        return questions;
    }

    public List<Question> getAllNoAnsweredQuestions(String username) {
        Profile profiles = profileRepository.findByUsername(username);
        List<Answer> participatedVotes = answerRepository.findAnswersByParticipatedUsersContains(profiles);
        List<Question> questions = questionRepository.findAll();
        List<Question> questionList = new ArrayList<>(questions);
        for (Question q: questions) {
            for (Answer a: participatedVotes) {
                if (q.getAnswers().contains(a) && q.getExpireDate().after(new Date())) {
                    questionList.remove(q);
                    break;
                }
            }
        }
        for (Question question:questionList) {
            question.setAnswers(answerRepository.findAllByQuestionId(question));
            question.setCountAnswers(getNumberOfAnswersByQuestion(question));
        }
        return questionList;
    }

    public int getNumberOfAnswersByQuestion(Question question) {
        int counter = 0;
        List<Answer> answers = answerRepository.findAllByQuestionId(question);
        for (Answer a: answers) {
            counter += a.getParticipatedUsers().size();
        }
        return counter;
    }

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    @Transactional
    public void delete(int id) {
        Question question = questionRepository.findById(id).get();
        for (Answer a: question.getAnswers()) {
            answerRepository.deleteById(a.getAnswerId());
        }
        questionRepository.delete(question);
    }

    public Question getQuestionById(int id) {
        return questionRepository.getOne(id);
    }

    public List<Question> getQuestionsByInterest(Interest interest) {
        return questionRepository.getAllByQuestionInterestsContains(interest);
    }

    public void updateQuestion(Question question) {
        Question updatedQuestion = questionRepository.getOne(question.getQuestionId());
        updatedQuestion.setQuestionName(question.getQuestionName());
        List<Answer> copyOflist = new ArrayList<>(updatedQuestion.getAnswers());
        int count = 0;
        for (Answer a: copyOflist) {
            a.setAnswer(question.getAnswers().get(count).getAnswer());
            answerRepository.save(a);
            count++;
        }
        updatedQuestion.getAnswers().addAll(question.getAnswers());
        questionRepository.save(updatedQuestion);
    }
}
