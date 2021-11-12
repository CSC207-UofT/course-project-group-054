/*
This file represents the controller class which handles the interactions between inputs and outputs.
 */
package com.example.compound.controller;

import java.util.*;

import com.example.compound.data.*;
import com.example.compound.entities.*;
import com.example.compound.use_cases.*;

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
        "Pay someone",
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
                    createExpenseView(inOut, currentUser, expenseTitle);
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
                    String expenseToPay = inOut.getInput();
                    inOut.sendOutput("Enter the amount you wish to pay");
                    String amountPaid = inOut.getInput();
                    try {
                        Double amount = Double.parseDouble(amountPaid);
                        ExpenseManager.payDebt(currentUser, expenseToPay, amount);
                    } catch(Exception E) {
                        System.out.println("Please enter a valid amount!");
                    }
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
     *  @param inOut the user interface object
     * @param u The user that is calling this function.
     * @param expenseTitle The title of the expense
     */
    public static void createExpenseView(InOut inOut, User u, String expenseTitle) {
        HashMap<Person, Double> borrowedSoFar = new HashMap<>();
        HashMap<Person, Double> lentSoFar = new HashMap<>();

        List<String> people = new ArrayList<>();
        people.add(currentUser.getEmail());

        inOut.sendOutput("Enter amount borrowed/lent: (0.00)");
        double amount = Float.parseFloat(inOut.getInput());

        inOut.sendOutput("Did you borrow (b) or lend (l)?");
        boolean userBorrow = inOut.getInput().equals("b");
        if (userBorrow){
            u.updateBalance(-amount);
        }
        else{
            u.updateBalance(amount);
        }

        boolean addMorePeople = Boolean.TRUE;
        do {
            inOut.sendOutput("Do you want to add more people to this expense? (y/n)");
            String input2 = inOut.getInput();
            switch (input2) {
                case "y" -> {
                    inOut.sendOutput("Enter their name:");
                    String name = inOut.getInput();

                    inOut.sendOutput("Enter user email:");
                    String email = inOut.getInput();

                    inOut.sendOutput("Did they borrow (b) or lend (l)?");
                    String borrowOrLend = inOut.getInput();

                    inOut.sendOutput("Enter the amount borrowed/lent: (0.00)");
                    String amountUsedStr = inOut.getInput();
                    double amountUsed = Double.parseDouble(amountUsedStr);

                    boolean borrowed = borrowOrLend.equals("b");
                    if (borrowed) {
                        amountUsed = -amountUsed;
                    }

                    // If we find the user in the database then update bal
                    if (UserManager.getUser(email) != null) {
                        User user = UserManager.getUser(email);
                        assert user != null;

                        if (amount > 0){
                            borrowedSoFar.put(user, amountUsed);
                        }
                        else {
                            lentSoFar.put(user, amountUsed);
                        }
                        user.updateBalance(amountUsed);
                    }
                    // Otherwise, create a stand in person.
                    else {
                        Person standIn = new Person(name, amountUsed, email);
                        if (amount > 0){
                            borrowedSoFar.put(standIn, amountUsed);
                        }
                        else {
                            lentSoFar.put(standIn, amountUsed);
                        }
                    }

                    System.out.println("Add more people?");
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
        currentUser.addExpense(
                Objects.requireNonNull(
                        Expense.createExpense(expenseTitle, amount, lentSoFar, borrowedSoFar)));
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
