package com.example.compound.cli_controllers;

import java.util.*;

import com.example.compound.entities.User;
import com.example.compound.entities.Person;
import com.example.compound.entities.Group;
import com.example.compound.repositories.GroupRepository;
import com.example.compound.use_cases.*;
import com.example.compound.use_cases.gateways.*;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;
import com.example.compound.use_cases.transfer_data.GroupTransferData;
import com.example.compound.use_cases.transfer_data.ItemTransferData;

/**
 * A Controller for handling the interactions between inputs and outputs.
 */
public class Controller {
    private static boolean isLoggedIn = Boolean.FALSE;
    private static final String appName = "Money Manager";
    private final RepositoryGatewayI<BudgetTransferData> budgetRepository;
    private final GroupRepository groupRepository;
    private final RepositoryGatewayI<ItemTransferData> itemRepository;
    private final RepositoryGateway repositoryGateway;
    private final GroupManager groupManager;
    private final UserManager userManager;
    private final ExpenseManager expenseManager;
    private final CurrentUserManager currentUserManager;
    private final PersonManager personManager;
    private static final String[] actions = {

    public static String[] actions = {
            "Add an expense",
            "Show groups",
            "Check balance",
            "Update Profile",
            "Create a new group",
            "Manage Groups",
            "View expenses",
            "Pay an expense",
            "Log out"
    };
    private static final String[] profileActions = {
            "Change Name",
            "Change Email",
            "Delete Account",
            "Back"
    };
    private static final String[] mainMenuOptions = {
            "Sign in to my account",
            "Create a new account",
            "Close app"
    };

    /**
     * Construct a new Controller with the given parameters.
     * @param budgetRepository  the repository for budgets
     * @param groupRepository   the repository for groups
     * @param itemRepository    the repository for items
     * @param repositoryGateway the repository for all objects
     */
    public Controller(RepositoryGatewayI<BudgetTransferData> budgetRepository,
                      GroupRepository groupRepository,
                      RepositoryGatewayI<ItemTransferData> itemRepository,
                      RepositoryGateway repositoryGateway) {
        this.budgetRepository = budgetRepository;
        this.groupRepository = groupRepository;
        this.itemRepository = itemRepository;
        this.repositoryGateway = repositoryGateway;
        this.groupManager = new GroupManager(this.repositoryGateway);
        this.userManager = new UserManager(this.repositoryGateway);
        this.expenseManager = new ExpenseManager(this.repositoryGateway);
        this.currentUserManager = new CurrentUserManager(this.repositoryGateway);
        this.personManager = new PersonManager(this.repositoryGateway);
    }

    /**
     * Have the user choose an option from the main menu and perform the appropriate action.
     * @param inOut the user interface object
     */
    public void menu(InOut inOut) {
        inOut.sendOutput("Welcome to " + appName);
        int menuInput = inOut.getOptionView(mainMenuOptions);
        switch (menuInput) {
            case 1 -> {
                // Login
                String email = inOut.requestInput("your Email");
                // set the current user
                currentUserManager.setCurrentUser(userManager.getUser(email));
                if (this.currentUserManager.getCurrentUser() != null) {
                    authenticateUser(this.currentUserManager.getCurrentUser().getEmail());
                    inOut.sendOutput("Welcome back, " + this.currentUserManager.getCurrentUser() .getName() + "!");
                    dashboard(inOut);
                } else {
                    inOut.sendOutput("ERROR: There was a problem logging you in. Please try again.");
                }
            }
            case 2 -> {
                // Sign up
                String email = inOut.requestInput("your email");
                String name = inOut.requestInput("your name");
                String password = getPassword(inOut);

                double balance = 0.0;
                userManager.createUser(name, balance, email, password);
                inOut.sendOutput("Thanks for signing up!");
            }
            case 3 -> System.exit(1);
            default -> System.out.println("Please enter a valid option.");
        }
    }

    /**
     * Request that the user input a password, confirm the input password, and return it.
     * @param inOut the user interface object
     * @return the password input by the user
     */
    private String getPassword(InOut inOut) {
        String password = inOut.requestInput("your password");
        String passwordConfirmation = inOut.requestInput("your password again");
        if (!password.equals(passwordConfirmation)) {
            inOut.sendOutput("The passwords do not match. Please try again.");
            return getPassword(inOut);
        }
        return password;
    }

    /**
     * While the user is logged in, have the user choose an action to perform on their account entities and perform
     * that action.
     * @param inOut the user interface object
     */
    public void dashboard(InOut inOut) {
        while (isLoggedIn) {
            // Return an integer between 1 and the number of actions, inclusive
            int input = inOut.getOptionView(actions);

            switch (input) {
                case 1 -> {
                    String expenseTitle = inOut.requestInput("the title");
                    ExpenseController expenseController = new ExpenseController(
                            this.currentUserManager, this.userManager, this.personManager,
                            this.expenseManager);
                    expenseController.createExpenseView(inOut, expenseTitle);
                }
                case 2 -> {
                    StringBuilder lst = this.groupManager.showListOfGroup(currentUserManager.getCurrentUser());
                    inOut.sendOutput(lst);
                }
                case 3 -> inOut.sendOutput("Your balance is: $" + currentUserManager.getCurrentUser().getBalance());
                case 4 -> {
                    inOut.sendOutput(userManager.getProfile(currentUserManager.getCurrentUser(), groupManager)); // Show the user's information
                    updateProfile(inOut);
                }
                case 5 -> createGroupView(inOut);
                case 6 -> {
                    GroupController groupController = new GroupController(
                            repositoryGateway, budgetRepository, groupRepository,
                            itemRepository, currentUserManager, expenseManager);
                    groupController.updateGroup(inOut);
                }//Manage Groups
                //TODO: Fix case 7; not properly displaying people in expenses
                case 7 -> inOut.sendOutput(this.userManager.getExpenses(currentUserManager.getCurrentUser()));
                case 8 -> {
                    String expenseToPay = inOut.requestInput("the EUID of the expense you wish to pay");
                    Double amount = requestDouble(inOut, "the amount you wish to pay");
                    String borrowed = inOut.requestInput("whether you borrowed: 'y' for yes or 'n' for no");
                    expenseManager.payDebt(currentUserManager.getCurrentUser(), expenseToPay, amount, borrowed.equals("y"));
                }
                case 9 -> {
                    logoutUser();
                    inOut.sendOutput("Goodbye. Have a nice day!");
                }
            }
        }
    }

    /**
     * A helper method that requests the user to enter input for the given attribute and converts the input string
     * returned by the given user interface object to a double.
     * @param inOut     the user interface object
     * @param attribute the attribute for which the user interface object requests the user to enter input
     * @return the double input by the user
     */
    private double requestDouble(InOut inOut, String attribute) {
        String input = inOut.requestInput(attribute);
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            inOut.sendOutput("Please enter a valid amount!");
            return requestDouble(inOut, attribute);
        }
    }

    /**
     * Create a new group based on user input.
     * If the current user is not authenticated, a new group is not created.
     * @param inOut the user interface object
     */
    public void createGroupView(InOut inOut) {
        if (getIsNotLoggedIn()) {
            inOut.sendOutput("Error: You must be authenticated to create a new group.");
        }

        List<String> members = new ArrayList<>();
        members.add(currentUserManager.getCurrentUser().getEmail());

        // Input the group's name
        String groupName = inOut.requestInput("the new group's name");

        // Input the names of the group's members
        boolean addAnotherMember = false;

        inOut.sendOutput("ADD GROUP MEMBERS:\nYou will now be asked to add group members. " +
                "Press enter if you don't want to add any member");

        // Loop while requesting to add group members.
        do {
            String member = inOut.requestInput("the email address of the member");
            if (member.equals("")) {
                continue;
            }
            members.add(member);

            String input = inOut.requestInput("whether you want to add more members (y/n)");

            if (input.equals("y")) {
                addAnotherMember = Boolean.TRUE;
            } else {
                addAnotherMember = Boolean.FALSE;
            }
        } while (addAnotherMember);

        // Request the group's description
        String description = inOut.requestInput("a description");

        // Create and add the group to our Database
        this.groupManager.createGroup(groupName, members, new ArrayList<>(), description);
    }

    /**
     * Authenticate the user; check if they're signed up.
     * @param email the user we are checking.
     */
    public void authenticateUser(String email) {
        currentUserManager.setCurrentUser(userManager.getUser(email));
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
     * Assign the status of the user to be logged out.
     */
    public void logoutUser() {
        // set current user to null
        currentUserManager.resetCurrentUser();
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
            int inputP = inOut.getOptionView(profileActions);
            switch (inputP){
                case 1 -> changeName(inOut);
                case 2 -> changeEmail(inOut);
                // Delete account
                case 3 -> {
                    repositoryGateway.removeUser(currentUserManager.getCurrentUser());
                    inOut.sendOutput("Your account has been successfully deleted.");
                    back = true;
                    isLoggedIn = false;
                }
                case 4 -> back = true;
            }
        }

    }

    /**
     * A helper method that helps to change the name of the currentUser.
     * @param inOut The user interface object.
     */
    public void changeName(InOut inOut) {
        String name = inOut.requestInput("the new name");
        UserManager.setName(currentUserManager.getCurrentUser(), name);
        inOut.sendOutput("Your name is changed successfully. Here's your new profile:");
        inOut.sendOutput(userManager.getProfile(currentUserManager.getCurrentUser(), groupManager));
    }

    /**
     * A helper method that helps to change the email of the currentUser.
     * @param inOut The user interface object.
     */
    public void changeEmail(InOut inOut) {
        String email = inOut.requestInput("the new email");
        userManager.setEmail(currentUserManager.getCurrentUser(), email);
        inOut.sendOutput("Your email is changed successfully. Here's your new profile:");
        inOut.sendOutput(userManager.getProfile(currentUserManager.getCurrentUser(), groupManager));
    }
}
