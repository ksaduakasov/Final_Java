package com.example.demo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int groupID;

    @Column(name = "group_name", unique = true)
    private String groupName;

    @Transient
    @OneToMany(mappedBy = "groupId", orphanRemoval = true)
    private List<Profile> students = new ArrayList<>();

    public Group() {
    }

    public Group(int groupID, String groupName) {
        this.groupID = groupID;
        this.groupName = groupName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Profile> getStudents() {
        return students;
    }

    public void setStudents(List<Profile> students) {
        this.students = students;
    }
}
