package controller;

//import java.sql.SQLOutput;
import java.util.*;

import use_cases.*;
import entities.*;
import data.*;
import view.*;

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

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Data.initializeData();

        do {
            View.menuView();
        } while (!isLoggedIn);

        System.out.println("\n");
    }
  
    public static void dashboard() {
        while (isLoggedIn) {
            int input = View.getActionView(actions); // Return an integer between 1 and the number of actions, inclusive
            switch (input) {
//                case 1 -> GroupManager.create_temp();
                case 1 -> createExpenseView();
                case 2 -> {
                    StringBuilder lst = ExpenseManager.show_group(currentUser);
                    System.out.println(lst);
                }
                case 3 -> System.out.println("Your balance is: $" + currentUser.getBalance());
                case 4 -> UserManager.updateProfile(currentUser);
                case 5 -> {
                    Group g1 = View.createGroupView();
                    if (g1 != null) {
                        Data.groups.add(g1);
                        View.outputGroups(); // For testing the code
                    }
                }
                case 6 -> System.out.println(UserManager.getExpenses(currentUser));
                case 7 -> {
                    currentUser = null;
                    isLoggedIn = Boolean.FALSE;
                    System.out.println("Goodbye. Have a nice day!");
                }
            }
        }
    }

    public static void createExpenseView() {
        double amount;
        List<String> people = new ArrayList<>();
        people.add(currentUser.getEmail());

        System.out.println("Enter amount: ");
        amount = Float.parseFloat(sc.nextLine());

        boolean isGroupExpense;

        // Asking User whether expense is a group expense
        System.out.println("Group expense (y/n): ");
        String input = sc.nextLine();

        // GROUP EXPENSE
        if (input.equals("y") || input.equals("Y")) {
            StringBuilder lst = ExpenseManager.show_group(currentUser);
            System.out.println(lst);
            System.out.print("Enter group name: ");
            String groupName = sc.nextLine();
            try {
                // TODO Implement this as Group.findGroup rather than directly
                for (Group group: Data.groups) {
                    if (group.getGroupName().equals(groupName)) {
                        if (Expense.createGroupExpense("", amount, currentUser.getUUID(), group)) {
                            System.out.println("Successfully added to your expenses.");
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("There was an error finding your group in our database.");
            }
//            System.out.println("Group expenses are not currently supported.");
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
            if (Expense.createExpense(amount, currentUser.getUUID(), people)) {
                System.out.println("Expense has been successfully created!");
                System.out.println(Data.expenses);
                System.out.println(currentUser.expenses.get(0));
            }
        } else {
            // TODO: Handle this
            System.out.println("Please enter a valid choice.");
        }
    }

    public static void authenticateUser(User user) {
        // TODO: Implement this method
        currentUser = user; // TODO Set it as indexOf user in Data.USER insetead of directly assigning user object
        isLoggedIn = Boolean.TRUE;
        System.out.println("Welcome back, " + currentUser.getName() + "!");
        dashboard();
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
