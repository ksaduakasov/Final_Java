package com.example.demo.services;

import com.example.demo.models.Group;
import com.example.demo.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public boolean addGroup(Group group) {
        Group groupFromDb = groupRepository.findByGroupName(group.getGroupName());
        if (groupFromDb == null) {
            groupRepository.save(group);
            return true;
        } else {
            return false;
        }
    }
}
