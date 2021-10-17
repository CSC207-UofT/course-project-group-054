package use_cases;

import data.Data;
import entities.*;

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

        return new StringBuilder("You don't have any expenses now.");
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
}
