package com.example.compound.use_cases.gateways;

import com.example.compound.entities.*;
import java.util.List;

public interface RepositoryGateway {
    void addGroup(Group group);

    void addExpense(Expense expense);

    void addUser(User user);

    void addBudget(Budget budget);

    void addItem(Item item);

    List<Group> getGroups();

    List<Expense> getExpenses();

    List<User> getUsers();

    List<Budget> getBudgets();

    List<Item> getItems();

    void removeGroup(Group group);

    void removeExpense(Expense expense);

    void removeUser(User user);

    void removeBudget(Budget budget);

    void removeItem(Item item);

    int getNewGUID();

    int getNewEUID();

    int getNewUUID();

    int getNewBUID();

    int getNewIUID();
}
