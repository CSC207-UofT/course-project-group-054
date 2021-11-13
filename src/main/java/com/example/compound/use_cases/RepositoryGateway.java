package com.example.compound.use_cases;

import com.example.compound.entities.*;
import java.util.List;

public interface RepositoryGateway {
    void addGroup(Group group);

    void addExpense(Expense expense);

    void addUser(User user);

    List<Group> getGroups();

    List<Expense> getExpenses();

    List<User> getUsers();

    int getNewGUID();

    int getNewEUID();

    int getNewUUID();
}
