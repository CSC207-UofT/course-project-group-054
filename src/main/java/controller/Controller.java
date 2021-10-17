/*
This file represents the controller class which handles the interactions between inputs and outputs.
 */
package controller;

import java.util.*;

import use_cases.*;
import entities.*;
import data.*;
import view.*;

public class Controller {

    private static User currentUser;
    private static boolean isLoggedIn = Boolean.FALSE;
    public static String appName = "Money Manager";
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Main method
     */
    public static void main(String[] args) {
        Data.initializeData();

        do {
            View.menuView();
        } while (!isLoggedIn);

        System.out.println("\n");

    }

    /**
     * Create the view where we interact with the functions of Expense.
     * @param u The user that is calling this function.
     * @param expenseTitle The title of the expense
     */
    public static void createExpenseView(User u, String expenseTitle) {
        double amount;
        List<String> people = new ArrayList<>();
        people.add(currentUser.getEmail());

        System.out.println("Enter amount: ");
        amount = Float.parseFloat(sc.nextLine());

        // Asking User whether expense is a group expense
        System.out.println("Group expense (y/n): ");
        String input = sc.nextLine();

        // GROUP EXPENSE
        if (input.equals("y") || input.equals("Y")) {
            StringBuilder lst = GroupManager.showGroup(currentUser);
            System.out.println(lst);
            System.out.print("Enter group name: ");
            String groupName = sc.nextLine();
            try {
                for (Group group: Data.groups) {
                    if (group.getGroupName().equals(groupName)) {
                        if (Expense.createGroupExpense(expenseTitle, amount, group)) {
                            System.out.println("Successfully added to your expenses.");
                            u.updateBalance(-amount);
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("There was an error finding your group in our database.");
            }
        }

        // NOT A GROUP EXPENSE
        else if (input.equals("n") || input.equals("N")) {

            boolean addMorePeople = Boolean.TRUE;
            do {
                System.out.println("Do you want to add more people to this expense? (y/n)");
                String input2 = sc.nextLine();
                switch (input2) {
                    case "y" -> {
                        System.out.println("Enter user email:");
                        people.add(sc.nextLine());
                    }
                    case "n" -> {
                        if (people.size() == 0) {
                            System.out.println("ERROR: You need to have at least one other person to share " +
                                    "expense with.");
                        } else {
                            addMorePeople = Boolean.FALSE;
                        }
                    }
                }
            } while (addMorePeople);
            if (Expense.createExpense(expenseTitle, amount, people)) {
                u.updateBalance(-amount);
                System.out.println("Expense has been successfully created!");
                System.out.println(Data.expenses);
                System.out.println(currentUser.expenses.get(0));
            }
        } else {
            System.out.println("Please enter a valid choice.");
        }

    }

    /**
     * Authenticate the user; check if they're signed up.
     * @param user - the user we are checking.
     */
    public static void authenticateUser(User user) {
        currentUser = user;
        isLoggedIn = Boolean.TRUE;
        System.out.println("Welcome back, " + currentUser.getName() + "!");
        View.dashboardView();
    }

    /**
     * Check if the user is logged into the system or not.
     * @return true, if user is logged in. False otherwise.
     */
    public static boolean getUserStatus() {
        return isLoggedIn;
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
        isLoggedIn = false;
    }
}
