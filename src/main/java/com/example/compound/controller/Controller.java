/*
This file represents the controller class which handles the interactions between inputs and outputs.
 */
package com.example.compound.controller;

import java.util.*;

import com.example.compound.entities.*;
import com.example.compound.use_cases.*;
import com.example.compound.use_cases.budget.interactors.BudgetCreationInteractor;

public class Controller {

    private static User currentUser;
    private static boolean isLoggedIn = Boolean.FALSE;
    public static String appName = "Money Manager";
    public RepositoryGateway repositoryGateway;
    public GroupManager groupManager;
    public UserManager userManager;
    public ExpenseManager expenseManager;

    public Controller(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway; // TODO: Take in as a parameter?
        this.groupManager = new GroupManager(this.repositoryGateway);
        this.userManager = new UserManager(this.repositoryGateway);
        this.expenseManager = new ExpenseManager(this.repositoryGateway);
    }

    public static String[] actions = {
        "Add an expense",
        "Show groups",
        "Check balance",
        "Update Profile [Coming soon]",
        "Create a new group",
        "View expenses",
        "Pay someone",
        "Add a budget",
        "Log out"
    };
    public static String[] mainMenuOptions = {
            "Sign in to my account",
            "Create a new account",
            "Close app"
    };

    public void menu(InOut inOut) {
        inOut.welcome(appName);
        int menuInput = inOut.getActionView(mainMenuOptions);
        switch (menuInput) {
            case 1 -> {
                // Login
                String email = inOut.requestInput("your Email");
                User user = userManager.getUser(email);
                if (user != null) {
                    authenticateUser(user);
                    inOut.outputLoginSuccessView(user.getName());
                    dashboard(inOut);
                } else {
                    inOut.outputLoginFailureView();
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
                    StringBuilder lst = this.groupManager.showGroup(currentUser);
                    inOut.sendOutput(lst);
                }
                case 3 -> inOut.sendOutput("Your balance is: $" + currentUser.getBalance());
                case 4 -> inOut.sendOutput("Feature not currently implemented.");
                case 5 -> createGroupView(inOut);
                case 6 -> inOut.sendOutput(this.userManager.getExpenses(currentUser));
                case 7 -> {
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
                case 8 -> addBudget(inOut);
                case 9 -> {
                    logoutUser();
                    inOut.sendOutput("Goodbye. Have a nice day!");
                }
            }
        }
    }

    // TODO: Call this method from a group dashboard
    public static void manageBudgets(Group group, InOut inOut) {
        // showBudgets, have user select a Budget using getActionView, one number per Budget (separate selectBudget method)
        // show this budget
        // getActionView: set maxSpend, ...
    }

    public void addBudget(InOut inOut) {
        String name = inOut.getBudgetNameView();
        double maxSpend = inOut.getBudgetMaxSpendView();
        String groupName = inOut.getGroupNameView(groupManager.showGroup(currentUser));
        BudgetCreationInteractor interactor = new BudgetCreationInteractor(null, null);

        if (interactor.create(groupName, "", name, new String[0], maxSpend, 0)) { // TODO: eliminate categories and timeSpan?
            inOut.outputBudgetCreationSuccess();
        } else {
            inOut.outputBudgetCreationFailure();
        }
    }

    /**
     * Create and return a new Group based on user input.
     * If the user is not authenticated, a new group is not created.
     * @param inOut the user interface object
     */
    public void createGroupView(InOut inOut) {
        if (getIsNotLoggedIn()) {
            inOut.outputCreateGroupAuthenticationFailure();
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
     *  @param inOut the user interface object
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
