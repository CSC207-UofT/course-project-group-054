package com.example.compound.use_cases;

import com.example.compound.entities.Expense;
import com.example.compound.entities.Group;
import com.example.compound.entities.Person;
import com.example.compound.use_cases.gateways.RepositoryGateway;

import java.util.ArrayList;
import java.util.List;

/*
This is the manager for Group, we edit the entity group through this class.
 */
public class GroupManager {
    private final RepositoryGateway repositoryGateway;

    public GroupManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }

    public StringBuilder showListOfGroup(Person p) {
        StringBuilder lst = new StringBuilder("List of groups:\n");
        int counter = 0;
        for (Group g: repositoryGateway.getGroups()) {
            if (g.getGroupMembers().contains(p.getEmail())) {
                lst.append(g);
                lst.append("\n");
                counter++;
            } //
        }

        if (counter > 0) {
            return lst;
        }

        return new StringBuilder("You don't have any groups now.\n");
    }

    public List<String> getListOfGroup(Person p){
        List<String> lst = new ArrayList<>();
        for (Group g: repositoryGateway.getGroups()) {
            if (g.getGroupMembers().contains(p.getEmail())) {
                lst.add(g.getGroupName());
            } //
        }
        return lst;
    }

    public Group getGroupByName(String name) {
        for (Group g: repositoryGateway.getGroups()) {
            if (g.getGroupName().equals(name)) {
                return g;
            }
        }
        return null;
    }

    public static void removeMember(Group g, String oldEmail) {
        g.getGroupMembers().remove(oldEmail);
    }

    public static void addMember(Group g, String email) {
        g.getGroupMembers().add(email);
    }

    public static void setGroupName(Group g, String newName) {
        g.setGroupName(newName);
    }

    public static StringBuilder showGroupMembers(Group g){
        StringBuilder lst = new StringBuilder("List of group members:\n");
        int counter = 0;
        for (String u: g.getGroupMembers()) {
            counter++;
            lst.append(counter).append(": ").append(u).append(".\n");
        }

        if (counter > 1) {
            return lst;
        }

        return new StringBuilder("You are the only one in the group.\n");
    }

    public void createGroup(String groupName, List<String> groupMembers,
                            List<Expense> expenseList, String description) {
        this.repositoryGateway.addGroup(new Group(groupName, groupMembers, expenseList, description));
    }
}
