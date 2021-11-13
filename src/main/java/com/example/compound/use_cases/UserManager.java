package com.example.compound.use_cases;

import com.example.compound.entities.*;

/*
This file represents the User Class manager. The entity User is changed here.
 */

public class UserManager {
    private final RepositoryGateway repositoryGateway;

    public UserManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }

    public StringBuilder getExpenses(User user) {
        StringBuilder lst = new StringBuilder("RECENT EXPENSES\n");
        lst.append("EUID  Title                People\n");
        lst.append("---------------------------------\n");
        int counter = 0;

        for (String expenseUID: user.expenses) {
            try {
                for (Expense expense: repositoryGateway.getExpenses()) {
                    if (expense.getEUID().equals(expenseUID)) {
                        lst.append(expense).append("\n");
                        counter++;
                    }
                }
            } catch (Exception ignored) { }
        }

        if (counter > 0) {
            return lst;
        }

        return new StringBuilder("You don't have any expenses now.");
    }

    /**
     * Get the user based off the email provided.
     *
     * @param email - email of the user.
     * @return The user associated with the email if it exists in the databse, null otherwise.
     */
    public User getUser(String email) {
        //TODO: fix non static usages
        try {
            for (Person person : repositoryGateway.getUsers()) {
                if (person.getEmail().equals((email))) {
                    return (User) person;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public User createUser(String name, double balance, String email) {
        User user = new User(name, balance, email);
        this.repositoryGateway.addUser(user);
        return user;
    }
}
