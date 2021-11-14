package com.example.compound.use_cases;

import com.example.compound.entities.Expense;
import com.example.compound.entities.Group;
import com.example.compound.entities.Person;

import java.util.List;

/*
This is the manager for Group, we edit the entity group through this class.
 */
public class GroupManager {
    private final RepositoryGateway repositoryGateway;

    public GroupManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }

    public StringBuilder showGroup(Person p){
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

        return new StringBuilder("You don't have any groups now.");
    }

    public void createGroup(String groupName, List<String> groupMembers,
                            List<Expense> expenseList, String description) {
        this.repositoryGateway.addGroup(new Group(groupName, groupMembers, expenseList, description));
    }
}
