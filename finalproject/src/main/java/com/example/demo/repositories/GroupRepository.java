package com.example.demo.repositories;

import com.example.demo.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findByGroupName(String groupName);
}
