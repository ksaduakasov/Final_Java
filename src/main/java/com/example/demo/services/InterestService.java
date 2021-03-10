package com.example.demo.services;

import com.example.demo.models.Interest;
import com.example.demo.repositories.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService {

    private final InterestRepository interestRepository;

    @Autowired
    public InterestService(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }

    public boolean addInterest(Interest interest) {
        Interest interestFromDb = interestRepository.findByInterestName(interest.getInterestName());
        if (interestFromDb == null) {
            interestRepository.save(interest);
            return true;
        } else {
            return false;
        }
    }

    public Interest getInterestById(int interestId) {
        return interestRepository.getOne(interestId);
    }
}
