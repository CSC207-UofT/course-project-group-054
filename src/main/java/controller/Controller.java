/*
This file represents the controller class which handles the interactions between inputs and outputs.
 */
package controller;

import java.util.*;

import use_cases.*;
import entities.*;
import data.*;

public class Controller {

    private static User currentUser;
    private static boolean isLoggedIn = Boolean.FALSE;
    public static String appName = "Money Manager";

    public static String[] actions = {
        "Add an expense",
        "Show groups",
        "Check balance",
        "Update Profile [Coming soon]",
        "Create a new group",
        "View expenses",
        "Pay someone [Coming soon]",
        "Log out"
    };
  
    /**
     * While the user is logged in, have the user choose an action to perform on their account entities and perform
     * that action.
     * 
     * @param inOut the user interface object
     */
    public static void dashboard(InOut inOut) {
        while (isLoggedIn) {
            // Return an integer between 1 and the number of actions, inclusive
            int input = inOut.getActionView(actions);
            
            switch (input) {
                case 1 -> {
                    inOut.sendOutput("Enter the title: ");
                    String expenseTitle = inOut.getInput();
                    createExpense(inOut, currentUser, expenseTitle);
                }
                case 2 -> {
                    StringBuilder lst = GroupManager.showGroup(currentUser);
                    inOut.sendOutput(lst);
                }
                case 3 -> inOut.sendOutput("Your balance is: $" + currentUser.getBalance());
                case 4 -> inOut.sendOutput("Feature not currently implemented.");
                case 5 -> {
                    Group g1 = inOut.createGroupView();
                    if (g1 != null) {
                        Data.groups.add(g1);
                    }
                }
                case 6 -> inOut.sendOutput(UserManager.getExpenses(currentUser));
                case 7 -> {
                    inOut.sendOutput("Enter the EUID of the expense you wish to pay");
                    String expensePaid = inOut.getInput();
                    ExpenseManager.payDebt(currentUser, expensePaid);
                }
                case 8 -> {
                    logoutUser();
                    inOut.sendOutput("Goodbye. Have a nice day!");
                }
            }
        }
    }

    /**
     * Create the view where we interact with the functions of Expense.
     * 
     * @param inOut the user interface object
     * @param u The user that is calling this function.
     * @param expenseTitle The title of the expense
     */
    public static void createExpense(InOut inOut, User u, String expenseTitle) {
        List<String> people = new ArrayList<>();
        people.add(currentUser.getEmail());

        inOut.sendOutput("Enter amount: ");
        double amount = Float.parseFloat(inOut.getInput());

        // Asking User whether expense is a group expense
        inOut.sendOutput("Group expense (y/n): ");
        String input = inOut.getInput();

        // GROUP EXPENSE
        if (input.equals("y") || input.equals("Y")) {
            StringBuilder lst = GroupManager.showGroup(currentUser);
            inOut.sendOutput(lst);
            inOut.sendOutput("Enter group name: ");
            String groupName = inOut.getInput();
            try {
                for (Group group: Data.groups) {
                    if (group.getGroupName().equals(groupName)) {
                        if (Expense.createGroupExpense(expenseTitle, amount, group)) {
                            inOut.sendOutput("Successfully added to your expenses.");
                            u.updateBalance(-amount);
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
            if (Expense.createExpense(expenseTitle, amount, people)) {
                u.updateBalance(-amount);
                inOut.sendOutput("Expense has been successfully created!");
                inOut.sendOutput(Data.expenses);
                inOut.sendOutput(currentUser.expenses.get(0));
            }
        } else {
            inOut.sendOutput("Please enter a valid choice.");
        }
    }

    /**
     * Authenticate the user; check if they're signed up.
     * @param user - the user we are checking.
     */
    public static void authenticateUser(User user) {
        currentUser = user;
        setUserStatus(true);
    }

    /**
     * Check if the user is logged into the system or not.
     * @return true, if user is logged in. False otherwise.
     */
    public static boolean getIsNotLoggedIn() {
        return !isLoggedIn;
    }

    /**
     * Set the current user's login status to the given value.
     * @param isLoggedIn the new login status of the current user
     */
    public static void setUserStatus(boolean isLoggedIn) {
        Controller.isLoggedIn = isLoggedIn;
    }

    /**
     * Get the person currently logged in.
     * @return current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Assign the status of the user to be logged out.
     */
    public static void logoutUser() {
        currentUser = null;
        setUserStatus(false);
    }
}
