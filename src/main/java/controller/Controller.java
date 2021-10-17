package controller;

import java.util.*;

import use_cases.*;
import entities.*;
import data.*;

public class Controller {

    private static User currentUser;
    private static boolean isLoggedIn = Boolean.FALSE;

    // TODO: Replace the following dummy variable for app name
    public static String appName = "[APP NAME]";

    public static String[] actions = {
        "Add an expense",
        "Show groups",
        "Check balance",
        "Update Profile [Coming soon]",
        "Create a new group",
        "View expenses",
        "Log out"
    };

    public static void dashboard(InOut inOut) {
        while (isLoggedIn) {
            int input = inOut.getActionView(actions); // Return an integer between 1 and the number of actions, inclusive
            switch (input) {
//                case 1 -> GroupManager.create_temp();
                case 1 -> createExpense(inOut);
                case 2 -> {
                    StringBuilder lst = ExpenseManager.show_group(currentUser);
                    inOut.sendOutput(lst);
                }
                case 3 -> inOut.sendOutput("Your balance is: $" + currentUser.getBalance());
                case 4 -> UserManager.updateProfile(currentUser);
                case 5 -> {
                    Group g1 = inOut.createGroupView();
                    if (g1 != null) {
                        Data.groups.add(g1);
                        inOut.outputGroups(); // For testing the code
                    }
                }
                case 6 -> inOut.sendOutput(UserManager.getExpenses(currentUser));
                case 7 -> {
                    currentUser = null;
                    isLoggedIn = Boolean.FALSE;
                    inOut.sendOutput("Goodbye. Have a nice day!");
                }
            }
        }
    }

    public static void createExpense(InOut inOut) {
        List<String> people = new ArrayList<>();
        people.add(currentUser.getEmail());

        inOut.sendOutput("Enter amount: ");
        double amount = Float.parseFloat(inOut.getInput());

        // Asking User whether expense is a group expense
        inOut.sendOutput("Group expense (y/n): ");
        String input = inOut.getInput();

        // GROUP EXPENSE
        if (input.equals("y") || input.equals("Y")) {
            StringBuilder lst = ExpenseManager.show_group(currentUser);
            inOut.sendOutput(lst);
            inOut.sendOutput("Enter group name: ");
            String groupName = inOut.getInput();
            try {
                // TODO Implement this as Group.findGroup rather than directly
                for (Group group: Data.groups) {
                    if (group.getGroupName().equals(groupName)) {
                        if (Expense.createGroupExpense("", amount, currentUser.getUUID(), group)) {
                            inOut.sendOutput("Successfully added to your expenses.");
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                inOut.sendOutput("There was an error finding your group in our database.");
            }
        }

        // NOT A GROUP EXPENSE
        else if (input.equals("n") || input.equals("N")) {

            boolean addMorePeople = Boolean.TRUE;
            do {
                inOut.sendOutput("Do you want to add more people to this expense? (y/n)");
                String input2 = inOut.getInput();
                switch (input2) {
                    case "y" -> {
                        inOut.sendOutput("Enter user email:");
                        people.add(inOut.getInput());
                    }
                    case "n" -> {
                        if (people.size() == 0) {
                            inOut.sendOutput("ERROR: You need to have at least one other person to share " +
                                    "expense with.");
                        } else {
                            addMorePeople = Boolean.FALSE;
                        }
                    }
                }
            } while (addMorePeople);
            if (Expense.createExpense(amount, currentUser.getUUID(), people)) {
                inOut.sendOutput("Expense has been successfully created!");
                inOut.sendOutput(Data.expenses);
                inOut.sendOutput(currentUser.expenses.get(0));
            }
        } else {
            // TODO: Handle this
            inOut.sendOutput("Please enter a valid choice.");
        }
    }

    public static void authenticateUser(User user) {
        // TODO: Implement this method
        currentUser = user; // TODO Set it as indexOf user in Data.USER insetead of directly assigning user object
        isLoggedIn = Boolean.TRUE;
    }

    /**
     * Returns user's unique identifier through email
     * @param email Email to search user
     * @return UUID of user is user with given email exists in Data.USERS, "0" otherwise
     */
    public static String getUUID(String email) {
        for (Person person: Data.users) {
            try {
                User user = (User) person;
                if (user.getEmail().equals(email)) {
                    return user.getUUID();
                }
            } catch (Exception ignored) { }
        }

        return "0";
    }

    public static User getUser(String email) {
        try {
            for (Person person: Data.users) {
                if (person.getEmail().equals((email))) {
                    return (User) person;
                }
            }
        } catch (Exception ignored) { }
        return null;
    }

    /**
     * Function
     * @return true, if user is logged in. False otherwise.
     */
    public static boolean getUserStatus() {
        return isLoggedIn;
    }

    /**
     * Set the current user's login status to the given value.
     */
    public static void setUserStatus(boolean isLoggedIn) {
        Controller.isLoggedIn = isLoggedIn;
    }

    /**
     * Function
     * @return current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    public static Expense getExpense(String expenseUID) {
        try {
            for (Expense expense: Data.expenses) {
                if (expense.getEUID().equals(expenseUID)) {
                    return expense;
                }
            }
        } catch (Exception ignored) { }
        return null;
    }

    public static void logoutUser() {
        currentUser = null;
        isLoggedIn = false;
    }
}
