/*
This file represents the controller class which handles the interactions between inputs and outputs.
 */
package com.example.compound.controller;

import java.util.*;

import com.example.compound.data.*;
import com.example.compound.entities.*;
import com.example.compound.use_cases.*;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;
import com.example.compound.use_cases.budget.gateways.ItemRepositoryGateway;
import com.example.compound.use_cases.group.GroupRepositoryGateway;

public class Controller {

    private static User currentUser;
    private static boolean isLoggedIn = Boolean.FALSE;
    public static String appName = "Money Manager";
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;
    private final ItemRepositoryGateway itemRepositoryGateway;
    public RepositoryGateway repositoryGateway;
    public GroupManager groupManager;
    public UserManager userManager;
    public ExpenseManager expenseManager;

    public Controller(BudgetRepositoryGateway budgetRepositoryGateway,
                      GroupRepositoryGateway groupRepositoryGateway,
                      ItemRepositoryGateway itemRepositoryGateway,
                      RepositoryGateway repositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway; // TODO: instantiate gateways here instead of injecting? or dependency injection?
        this.groupRepositoryGateway = groupRepositoryGateway;
        this.itemRepositoryGateway = itemRepositoryGateway;
        this.repositoryGateway = repositoryGateway; // TODO: Take in as a parameter?
        this.groupManager = new GroupManager(this.repositoryGateway);
        this.userManager = new UserManager(this.repositoryGateway);
        this.expenseManager = new ExpenseManager(this.repositoryGateway);
    }

    public static String[] actions = {
            "Add an expense",
            "Show groups",
            "Check balance",
            "Update Profile",
            "Create a new group",
            "Manage Groups",
            "View expenses",
            "Pay someone",
            "Log out"
    };

    public static String[] profileActions = {
            "Change Name",
            "Change Email",
            "Delete Account",
            "Back"
    };

    public static String[] mainMenuOptions = {
            "Sign in to my account",
            "Create a new account",
            "Close app"
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

    public void menu(InOut inOut) {
        inOut.sendOutput("Welcome to " + appName);
        int menuInput = inOut.getActionView(mainMenuOptions);
        switch (menuInput) {
            case 1 -> {
                // Login
                String email = inOut.requestInput("your Email");
                User user = userManager.getUser(email);
                if (user != null) {
                    authenticateUser(user);
                    inOut.sendOutput("Welcome back, " + user.getName() + "!");
                    dashboard(inOut);
                } else {
                    inOut.sendOutput("ERROR: There was a problem logging you in. Please try again.");
                }
            }
            case 2 -> {
                // Sign up
                String email = inOut.requestInput("your Email");
                String name = inOut.requestInput("your Name");
                double balance = 0.0;
                userManager.createUser(name, balance, email);
                inOut.sendOutput("Thanks for signing up!");
            }
            case 3 -> {
                // Create Group

            }
            case 4 -> System.exit(1);
            default -> System.out.println("Please enter a valid option.");
        }
    }

    /**
     * While the user is logged in, have the user choose an action to perform on their account entities and perform
     * that action.
     *
     * @param inOut the user interface object
     */
    public void dashboard(InOut inOut) {
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
                    StringBuilder lst = this.groupManager.showListOfGroup(currentUser);
                    inOut.sendOutput(lst);
                }
                case 3 -> inOut.sendOutput("Your balance is: $" + currentUser.getBalance());
                case 4 -> {
                    inOut.sendOutput(userManager.getProfile(currentUser, groupManager)); // Show the user's information
                    updateProfile(inOut);
                }
                case 5 -> createGroupView(inOut);
                case 6 -> updateGroup(inOut); //Manage Groups
                case 7 -> inOut.sendOutput(this.userManager.getExpenses(currentUser));
                case 8 -> {
                    inOut.sendOutput("Enter the EUID of the expense you wish to pay");
                    String expenseToPay = inOut.getInput();
                    inOut.sendOutput("Enter the amount you wish to pay");
                    String amountPaid = inOut.getInput();
                    try {
                        Double amount = Double.parseDouble(amountPaid);
                        expenseManager.payDebt(currentUser, expenseToPay, amount);
                    } catch(Exception E) {
                        System.out.println("Please enter a valid amount!");
                    }
                }
                case 9 -> {
                    logoutUser();
                    inOut.sendOutput("Goodbye. Have a nice day!");
                }
            }
        }
    }

    // TODO: Call this method from a group dashboard
    public void manageBudgets(Group group, InOut inOut) {
        new BudgetController(group.getGUID(), budgetRepositoryGateway, groupRepositoryGateway,
                itemRepositoryGateway, repositoryGateway, expenseManager).selectionDashboard(inOut);
    }

    /**
     * Create and return a new Group based on user input.
     * If the user is not authenticated, a new group is not created.
     * @param inOut the user interface object
     */
    public void createGroupView(InOut inOut) {
        if (getIsNotLoggedIn()) {
            inOut.sendOutput("Error: You must be authenticated to create a new group.");
        }

        List<String> members = new ArrayList<>();
        members.add(getCurrentUser().getEmail());

        // Input the group's name
        String groupName = inOut.requestInput("the new group's name");

        // Input the names of the group's members
        boolean addAnotherMember = false;

        inOut.sendOutput("ADD GROUP MEMBERS:\nYou will now be asked to add group members. " +
                "Press enter if you don't want to add any member");

        // Loop while requesting to add group members.
        do {
            String member = inOut.requestInput("the email address of the member: ");
            if (member.equals("")) {
                continue;
            }
            members.add(member);

            inOut.requestInput("whether you want to add more members (y/n)");

            if (inOut.getInput().equals("y")) {
                addAnotherMember = Boolean.TRUE;
            } else {
                addAnotherMember = Boolean.FALSE;
            }
        } while (addAnotherMember);

        // Request the group's description
        String description = inOut.requestInput("a description");

        // Create and add the group to our Database
//        Group group =
        this.groupManager.createGroup(groupName, members, new ArrayList<>(), description);
//        Data.groups.add(group);
    }

    /**
     * Create the view where we interact with the functions of Expense.
     *
     * @param inOut the user interface object
     * @param u The user that is calling this function.
     * @param expenseTitle The title of the expense
     */
    public void createExpenseView(InOut inOut, User u, String expenseTitle) {
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
                    if (userManager.getUser(email) != null) {
                        User user = userManager.getUser(email);
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
                        expenseManager.createExpense(expenseTitle, amount, lentSoFar, borrowedSoFar, userManager)));
    }

    /**
     * A helper method that enables current user to add or remove people to an expense or a group.
     * @param inOut The interface for the view
     * @param addOrRemove Add or Remove
     * @param expenseOrGroup Expense or Group
     * @return A list of strings of people to be added or removed
     */
    public List<String> addRemovePeople(InOut inOut, String addOrRemove, String expenseOrGroup) {
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
    public void authenticateUser(User user) {
        currentUser = user;
        setUserStatus(true);
    }

    /**
     * Check if the user is logged into the system or not.
     * @return true, if user is logged in. False otherwise.
     */
    public boolean getIsNotLoggedIn() {
        return !isLoggedIn;
    }

    /**
     * Set the current user's login status to the given value.
     * @param isLoggedIn the new login status of the current user
     */
    public void setUserStatus(boolean isLoggedIn) {
        Controller.isLoggedIn = isLoggedIn;
    }

    /**
     * Get the person currently logged in.
     * @return current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Assign the status of the user to be logged out.
     */
    public void logoutUser() {
        currentUser = null;
        setUserStatus(false);
    }

    /**
     * While the users want to update their profile, have the user choose an action to perform on their profile
     * and perform that action.
     * @param inOut the user interface object
     */
    public void updateProfile(InOut inOut){
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
                    repositoryGateway.removeUser(currentUser);
                    inOut.sendOutput("Your account has been successfully deleted.");
                    back = true;
                    isLoggedIn = false;
                }
                case 4 -> back = true;
            }
        }

    }

    public void changeName(InOut inOut) {
        inOut.sendOutput("Please enter the new name.");
        String name = inOut.getInput();
        UserManager.setName(currentUser, name);
        inOut.sendOutput("Your name is changed successfully. Here's your new profile:");
        inOut.sendOutput(userManager.getProfile(currentUser, groupManager));
    }

    public void changeEmail(InOut inOut) {
        inOut.sendOutput("Please enter the new email.");
        String email = inOut.getInput();
        userManager.setEmail(currentUser, email);
        inOut.sendOutput("Your email is changed successfully. Here's your new profile:");
        inOut.sendOutput(userManager.getProfile(currentUser, groupManager));
    }


    /**
     * While the users want to update their group(s), have the user choose an action to perform on their group
     * and perform that action.
     * @param inOut the user interface object
     */
    private void updateGroup(InOut inOut) {
        boolean back = false;
        while(!back) {
            StringBuilder lst = groupManager.showListOfGroup(currentUser);
            inOut.sendOutput(lst);
            if (lst.charAt(0) == 'Y') {
                break;
            }
            inOut.sendOutput("Please select the group you wish to edit.");
            String groupName = inOut.getInput();
            if (!groupManager.getListOfGroup(currentUser).contains(groupName)){
                inOut.sendOutput("Please enter a valid group name.\n");
                break;
            }
            Group g = groupManager.getGroupByName(groupName);
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
    private boolean manageGroup(InOut inOut, boolean back, Group g, int inputG) {
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
                    repositoryGateway.removeGroup(g); //Delete Group
            case 7 -> back = true;
        }
        return back;
    }
}
