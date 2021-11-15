package com.example.compound.data;

import com.example.compound.entities.*;
import com.example.compound.use_cases.RepositoryGateway;

import java.util.*;

/*
The class representing the data.
 */
public class Data implements RepositoryGateway {
    public List<User> users = new ArrayList<>();
    public List<Expense> expenses = new ArrayList<>();
    public List<Group> groups = new ArrayList<>();
    public List<Budget> budgets = new ArrayList<>();
    public List<Item> items = new ArrayList<>();
    private int budgetCounter = 0;
    private int itemCounter = 0;

    public void initializeData() {
        // Creating dummy users
        users.add(new User("Rohan", 100.0, "rohan.tinna@mail.utoronto.ca"));
        users.add(new User("Johny", 100.0, "johny@example.com"));

        // Creating dummy groups
        groups.add(
                new Group("One Direction", new ArrayList<>() {
                    {
                        add("rohan.tinna@mail.utoronto.ca");
                        add("johny@example.com");
                    }
                }, new ArrayList<>(), "")
        ); // Group with 2 users

        groups.add(
                new Group("Avengers", new ArrayList<>() {
                    {
                        add("rohan.tinna@mail.utoronto.ca");
                    }
                }, new ArrayList<>(), "")
        ); // Group with 1 user

        groups.add(
                new Group("Impossible Group", new ArrayList<>(), new ArrayList<>(), "")
        ); // Empty group
    }

    public List<String> getExpenseStrings() {
        List<String> expenseStrings = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseStrings.add(expense.toString());
        }
        return expenseStrings;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addBudget(Budget budget) {
        this.budgets.add(budget);
        this.budgetCounter++;
    }

    public void addItem(Item item) {
        this.items.add(item);
        this.itemCounter++;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public List<Expense> getExpenses() {
        return this.expenses;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Budget> getBudgets() {
        return this.budgets;
    }

    public List<Item> getItems() {
        return this.items;
    }

    @Override
    public void removeGroup(Group group) {
        this.groups.remove(group);
    }

    @Override
    public void removeExpense(Expense expense) {
        this.expenses.remove(expense);
    }

    @Override
    public void removeUser(User user) {
        this.users.remove(user);
    }

    @Override
    public void removeBudget(Budget budget) {
        this.budgets.remove(budget);
    }

    @Override
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public int getNewGUID() {
        return groups.size();
    }

    public int getNewEUID() {
        return expenses.size();
    }

    public int getNewUUID() {
        return users.size();
    }

    public int getNewBUID() {
        return this.budgetCounter;
    }

    public int getNewIUID() {
        return this.itemCounter;
    }
}
