package com.example.compound.use_cases;

import com.example.compound.data.Data;
import com.example.compound.entities.*;

/*
This file represents the User Class manager. The entity User is changed here.
 */

public class UserManager {

    public static StringBuilder getExpenses(User user) {
        StringBuilder lst = new StringBuilder("RECENT EXPENSES\n");
        lst.append("EUID  Title                People\n");
        lst.append("---------------------------------\n");
        int counter = 0;

        for (String expenseUID: user.expenses) {
            try {
                for (Expense expense: Data.expenses) {
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
     * Get the user based off the email provided.
     *
     * @param email - email of the user.
     * @return The user associated with the email.
     */
    public static User getUser(String email) {
        try {
            for (Person person : Data.users) {
                if (person.getEmail().equals((email))) {
                    return (User) person;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Get the profile of the user
     *
     * @param user The user that needs to get the profile.
     * @return A string that shows the user's name, email, balance, list of expenses and groups.
     */
    public static StringBuilder getProfile(User user){
        StringBuilder out = new StringBuilder();
        out.append("Name: ").append(user.getName()).append(",\n");
        out.append("Email: ").append(user.getEmail()).append(",\n");
        out.append("Balance: ").append(user.getBalance()).append(",\n");
        out.append("Expense(s): \n").append(getExpenses(user));
        out.append(GroupManager.showListOfGroup(user));
        return out;
    }

    /**
     * Change the name of the user
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
    public static void setEmail(User user, String email) {
        String oldEmail = user.getEmail();
        for (Group g: Data.groups) {
            if (g.getGroupMembers().contains(oldEmail)) {
                GroupManager.removeMember(g, oldEmail);
                GroupManager.addMember(g, email);
            } //
        }
        user.setEmail(email);
    }
}
