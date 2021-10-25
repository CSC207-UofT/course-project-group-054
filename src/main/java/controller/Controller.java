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

    public enum ExpenseType {
        GROUP, NON_GROUP
    }
  
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
                case 1 -> createExpense(inOut);
                case 2 -> {
                    StringBuilder lst = GroupManager.showGroups(currentUser);
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
     * Create either a new group expense or a new non-group expense, according to user input.
     *
     * @param inOut the user interface object
     */
    public static void createExpense(InOut inOut) {
        String title = inOut.getExpenseTitleView();
        double amount = inOut.getExpenseAmountView();
        ExpenseType expenseType = inOut.getExpenseType();

        try {
            if (expenseType == ExpenseType.GROUP) {
                createGroupExpense(inOut, title, amount);
            } else {
                createNonGroupExpense(inOut, title, amount);
            }
        } catch (NullPointerException e) {
            inOut.outputExpenseExceptionResult();
        }
    }

    /**
     * Create a new group expense with the given title and amount. The expense is associated with the group that is
     * chosen by the current user.
     *
     * @param inOut the user interface object
     * @param title the title of the expense
     * @param amount the amount of the expense
     * @throws NullPointerException If any of the members of the group chosen by the user do not exist.
     */
    public static void createGroupExpense(InOut inOut, String title, double amount) throws NullPointerException {
        String groupName = inOut.getExpenseGroupNameView(GroupManager.showGroups(currentUser));

        if (ExpenseManager.createGroupExpense(title, amount, groupName, currentUser)) {
            inOut.outputGroupExpenseCreationSuccess();
        } else {
            inOut.outputGroupExpenseCreationFailure();
        }
    }

    /**
     * Create a new non-group expense with the given title and amount. The expense is associated with the users whose
     * email addresses are input by the current user.
     *
     * @param inOut the user interface object
     * @param title the title of the expense
     * @param amount the amount of the expense
     * @throws NullPointerException If any of the email addresses in the list input by the user is not the email
     *                              address of any available User.
     */
    public static void createNonGroupExpense(InOut inOut, String title, double amount) throws NullPointerException {
        List<String> people = inOut.getPeopleNonGroupExpenseView();
        people.add(currentUser.getEmail());

        ExpenseManager.createExpense(title, amount, people, currentUser);
        // TODO: should it be currentUser.expenses.get(0)?
        inOut.outputNonGroupExpenseCreationSuccess(Data.getExpenseStrings(), currentUser.expenses.get(0), people);
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
