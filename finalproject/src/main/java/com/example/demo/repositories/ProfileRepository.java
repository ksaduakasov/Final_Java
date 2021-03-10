package com.example.demo.repositories;

import com.example.demo.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByUsername(String username);
    List<Profile> findAllByUsername(String username);
}
