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
            "Update Profile",
            "Create a new group",
            "Manage Groups",
            "View expenses",
            "Pay someone [Coming soon]",
            "Log out"
    };
    public static String[] profileActions = {
            "Change Name",
            "Change Email",
            "Delete Account",
            "Back"
    };

    public static String[] groupActions = {
            "Edit Group Name",
            "Add People to Group",
            "Remove People",
            "View GroupMembers",
            "Leave Group",
            "Delete Group",
            "Back"
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
                    StringBuilder lst = GroupManager.showListOfGroup(currentUser);
                    inOut.sendOutput(lst);
                }
                case 3 -> inOut.sendOutput("Your balance is: $" + currentUser.getBalance());
                case 4 -> {
                    inOut.sendOutput(UserManager.getProfile(currentUser)); // Show the user's information
                    updateProfile(inOut);
                }
                case 5 -> {
                    Group g1 = inOut.createGroupView();
                    if (g1 != null) {
                        Data.groups.add(g1);
                    }
                }
                case 6 -> updateGroup(inOut); //Manage Groups
                case 7 -> inOut.sendOutput(UserManager.getExpenses(currentUser));
                case 8 -> {
                    inOut.sendOutput("Enter the EUID of the expense you wish to pay");
                    String expensePaid = inOut.getInput();
                    ExpenseManager.payDebt(currentUser, expensePaid);
                }
                case 9 -> {
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
        inOut.sendOutput("Enter amount: ");
        double amount = Float.parseFloat(inOut.getInput());

        // Asking User whether expense is a group expense
        inOut.sendOutput("Group expense (y/n): ");
        String input = inOut.getInput();

        // GROUP EXPENSE
        if (input.equals("y") || input.equals("Y")) {
            StringBuilder lst = GroupManager.showListOfGroup(currentUser);
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

            List<String> people = addRemovePeople(inOut, "add", "expense");
            people.add(currentUser.getEmail());
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
     * A helper method that enables current user to add or remove people to an expense or a group.
     * @param inOut The interface for the view
     * @param addOrRemove Add or Remove
     * @param expenseOrGroup Expense or Group
     * @return A list of strings of people to be added or removed
     */
    public static List<String> addRemovePeople(InOut inOut, String addOrRemove, String expenseOrGroup) {
        List<String> people = new ArrayList<>();
        boolean addRemovePeople = Boolean.TRUE;
        do {
            inOut.sendOutput("Do you want to " + addOrRemove + " more people in your "
                    + expenseOrGroup + "? (y/n)");
            String input2 = inOut.getInput();
            switch (input2) {
                case "y" -> {
                    inOut.sendOutput("Enter user email:");
                    people.add(inOut.getInput());
                }
                case "n" -> {
                    if (people.size() == 0 && expenseOrGroup.equals("expense")) {
                        inOut.sendOutput("ERROR: You need to " + addOrRemove + " at least one person in your " +
                                expenseOrGroup + ".");
                    } else {
                        addRemovePeople = Boolean.FALSE;
                    }
                }
            }
        } while (addRemovePeople);
        return people;
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

    /**
     * While the users want to update their profile, have the user choose an action to perform on their profile
     * and perform that action.
     * @param inOut the user interface object
     */
    public static void updateProfile(InOut inOut){
        boolean back = false;
        while(!back){
            int inputP = inOut.getActionView(profileActions);
            switch (inputP){
                case 1 -> changeName(inOut);
                case 2 -> changeEmail(inOut);
            /*
            Delete Account
             */
                case 3 -> {
                    Data.users.remove(currentUser);
                    inOut.sendOutput("Your account has been successfully deleted.");
                    back = true;
                    isLoggedIn = false;
                }
                case 4 -> back = true;
            }
        }
        
    }

    public static void changeName(InOut inOut) {
        inOut.sendOutput("Please enter the new name.");
        String name = inOut.getInput();
        UserManager.setName(currentUser, name);
        inOut.sendOutput("Your name is changed successfully. Here's your new profile:");
        inOut.sendOutput(UserManager.getProfile(currentUser));
    }

    public static void changeEmail(InOut inOut) {
        inOut.sendOutput("Please enter the new email.");
        String email = inOut.getInput();
        UserManager.setEmail(currentUser, email);
        inOut.sendOutput("Your email is changed successfully. Here's your new profile:");
        inOut.sendOutput(UserManager.getProfile(currentUser));
    }


    /**
     * While the users want to update their group(s), have the user choose an action to perform on their group
     * and perform that action.
     * @param inOut the user interface object
     */
    private static void updateGroup(InOut inOut) {
        boolean back = false;
        while(!back) {
            StringBuilder lst = GroupManager.showListOfGroup(currentUser);
            inOut.sendOutput(lst);
            if (lst.charAt(0) == 'Y') {
                break;
            }
            inOut.sendOutput("Please select the group you wish to edit.");
            String groupName = inOut.getInput();
            if (!GroupManager.getListOfGroup(currentUser).contains(groupName)){
                inOut.sendOutput("Please enter a valid group name.\n");
                break;
            }
            Group g = GroupManager.getGroupByName(groupName);
            assert g != null;
            int inputG = inOut.getActionView(groupActions);
            back = manageGroup(inOut, back, g, inputG);
        }

    }

    /**
     * A helper method extracted from the updateGroup method that involves all the optional actions on groups.
     * @param inOut the user interface object
     * @param back the while-loop "indicator"
     * @param g the group that the user selected
     * @param inputG the option that the user chose
     * @return the while-loop "indicator"
     */
    private static boolean manageGroup(InOut inOut, boolean back, Group g, int inputG) {
        switch (inputG){
            case 1 -> {
                inOut.sendOutput("Please enter the new name.");
                String newName = inOut.getInput();
                GroupManager.setGroupName(g, newName);
            } //Edit Group Name
            case 2 -> {
                List<String> people = addRemovePeople(inOut, "add", "group");
                for (String p: people) {
                    GroupManager.addMember(g, p);
                }
            } // Add people to the group
            case 3 -> {
                StringBuilder members = GroupManager.showGroupMembers(g);
                if (members.charAt(0) == 'Y') {
                    inOut.sendOutput("You should delete the group instead.");
                    break;
                }
                inOut.sendOutput("Note that invalid name would be automatically ignored.");
                List<String> people = addRemovePeople(inOut, "remove", "group");
                if (people.contains(currentUser.getName())){
                    people.remove(currentUser.getName());
                    inOut.sendOutput("You should leave or delete the group instead.");
                }
                for (String p: people) {
                    try {
                        GroupManager.removeMember(g, p);
                    } catch (Exception ignored) {}
                }
            } //Remove People from the group
            case 4 -> inOut.sendOutput(GroupManager.showGroupMembers(g)); //View GroupMembers
            case 5 -> //TODO: Need to update the balance of the current user.
                    GroupManager.removeMember(g, currentUser.getEmail()); //Leave Group
            case 6 -> //TODO: Need to update the balance of all the users in the group.
                    Data.groups.remove(g); //Delete Group
            case 7 -> back = true;
        }
        return back;
    }
}
