package com.example.compound.use_cases.group;

import com.example.compound.entities.Group;
import com.example.compound.entities.Budget;

import java.util.List;

public interface GroupRepositoryGateway {
    Group findById(String GUID);

    List<Group> findAll();

    Group save(Group group);

    void deleteById(String GUID);

//    // ...
//    boolean addBudget(Group group, Budget budget); // TODO: Create a new data transfer object (see https://piazza.com/class/kt4hlydpsym1bz?cid=843)
//
//    boolean addExpenses(Group group, List<Expense> expenses);
//
//    boolean removeBudget(Group group, String BUID);
}
