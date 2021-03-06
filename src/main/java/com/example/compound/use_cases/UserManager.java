package com.example.compound.use_cases;

import com.example.compound.entities.*;
import com.example.compound.use_cases.gateways.RepositoryGateway;

import java.util.Random;

/**
 * A use case class containing functions for managing Users.
 */
public class UserManager {
    private final RepositoryGateway repositoryGateway;

    /**
     * Construct a new GroupManager with the given parameters.
     * @param repositoryGateway the repository for all objects
     */
    public UserManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }

    /**
     * Return a String representation of the given user's list of expenses.
     * @param user the user for which the list of expenses is to be returned
     * @return a String representation of the given user's list of expenses
     */
    public StringBuilder getExpenses(User user) {
        StringBuilder lst = new StringBuilder("RECENT EXPENSES\n");
        lst.append("EUID | Title | People | Amount\n");
        lst.append("---------------------------------\n");
        int counter = 0;

        for (String expenseUID: user.getExpenses()) {
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

        return new StringBuilder("You don't have any expenses now.\n");
    }

    /**
     * Get the user from the email provided.
     * @param email the email of the user.
     * @return The user associated with the email if it exists in the databse, null otherwise.
     */
    public User getUser(String email) {
        try {
            for (User user : repositoryGateway.getUsers()) {
                if (user.getEmail().equals((email))) {
                    return user;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public User createUser(int uuid, String name, double balance, String email, String password) {
        User user = new User(uuid, name, email, email, balance, password);
        this.repositoryGateway.addUser(user);
        return user;
    }

    /**
     * Create a new user with the given parameters.
     * @param name     the user's name
     * @param balance  the user's balance
     * @param email    the user's email
     * @param password the user's password
     * @return the new user
     */
    public User createUser(String name, double balance, String email, String password) {
        int tempInt = this.generateUUID();
        User user = new User(tempInt, name, email, email, balance, password);
        this.repositoryGateway.addUser(user);
        return user;
    }
    /**
     * Get the profile of the user.
     * @param user The user that needs to get the profile.
     * @return A string that shows the user's name, email, balance, list of expenses and groups.
     */
    public StringBuilder getProfile(User user, GroupManager groupManager){
        StringBuilder out = new StringBuilder();
        out.append("Name: ").append(user.getName()).append(",\n");
        out.append("Email: ").append(user.getEmail()).append(",\n");
        out.append("Balance: ").append(user.getBalance()).append(",\n");
        out.append("Expense(s): \n").append(getExpenses(user));
        out.append(groupManager.showListOfGroup(user));
        return out;
    }

    /**
     * Change the name of the user.
     * @param user The user that needs to change the name.
     * @param name The new name of the user.
     */
    public static void setName(User user, String name) {
        user.setName(name);
    }

    /**
     * Change the email of the user
     * @param user The user that needs to change the email.
     * @param email The new email of the user.
     */
    public void setEmail(User user, String email) {
        String oldEmail = user.getEmail();
        for (Group g: repositoryGateway.getGroups()) {
            if (g.getGroupMembers().contains(oldEmail)) {
                GroupManager.removeMember(g, oldEmail);
                GroupManager.addMember(g, email);
            } //
        }
        user.setEmail(email);
    }

    /**
     * Generate a UUID for a new user.
     * @return a new UUID
     */
    public int generateUUID() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }
}
