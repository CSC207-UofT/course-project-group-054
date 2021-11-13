package com.example.compound.data;

import com.example.compound.entities.*;
import com.example.compound.use_cases.RepositoryGateway;

import java.util.*;

/*
The class representing the data.
 */
public class Data implements RepositoryGateway {
    public List<Person> persons = new ArrayList<>();
    public List<Expense> expenses = new ArrayList<>();
    public List<Group> groups = new ArrayList<>();

    public void initializeData() {
        // Creating dummy users
        persons.add(new User("Rohan", 100, "rohan.tinna@mail.utoronto.ca"));
        persons.add(new User("Johny", 100, "johny@example.com"));

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

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public List<Expense> getExpenses() {
        return this.expenses;
    }

    public List<Person> getPersons() {
        return this.persons;
    }

}
