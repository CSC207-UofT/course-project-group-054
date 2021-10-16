package Controller;

import java.util.*;

import use_cases.*;
import Entities.*;
import Data.*;
import use_cases.UserManager;

public class Controller {

    private static User currentUser;
    private static boolean isLoggedIn = Boolean.FALSE;

    // TODO: Replace the following dummy variable for app name
    private static String appName = "Money Manager";
    public static String[] mainMenuOptions = {"Sign in to my account", "Create a new account"};

    private static Scanner sc = new Scanner(System.in);

    public static User welcomeScreen() {
        // For now we will have to sign up each time
        String[] outputs = {"Full Name (*): ", "Email (*): ", "Phone: "};
        List<String> inputs = new ArrayList<>();

        System.out.println("Please enter the following information and press enter. Fields marked (*) are required.");
        for (String output: outputs) {
            System.out.println(output);
            String input = sc.nextLine();
            inputs.add(input);
        }

        currentUser = new User(inputs.get(0),0,inputs.get(2));

        System.out.println("Thanks for signing up! Your account has been successfully created, " + inputs.get(0) + ".");

        return currentUser;
    }


    public static void main(String[] args) {

        isLoggedIn = Boolean.TRUE;
        System.out.println("Welcome to " + appName);
        for (int i = 1; i <= mainMenuOptions.length; i++) {
            System.out.println(i + ") " + mainMenuOptions[i - 1]);
        }
        System.out.println("\n");
        currentUser = welcomeScreen(); // see welcomeScreen comment

        while (isLoggedIn) {
            System.out.println("""
                    Please enter the number for the actions below:
                    1. Add an expense
                    2. Manage groups
                    3. Check balance
                    4. Update Profile
                    5. Log out
                    6. Create a new group""");
            String input = sc.nextLine();
            switch (input) {
                case "1" -> createExpenseView();
                case "2" -> GroupManager.showGroup(currentUser);
                case "3" -> UserManager.showBalance(currentUser);
                case "4" -> UserManager.updateProfile(currentUser);
                case "5" -> {
                    System.out.println("Goodbye. Have a nice day!");
                    isLoggedIn = Boolean.FALSE;
                }
                case "6" -> createGroupView(currentUser);
                default -> {
                    System.out.println("Please select a valid option.");
                }
            }
        }
    }

    public static void createExpenseView() {
        double amount;
        List<String> people = new ArrayList<>();

        System.out.println("Enter amount: ");
        amount = Float.parseFloat(sc.nextLine());

        boolean isGroupExpense;

        // Asking User whether expense is a group expense
        System.out.println("Group expense (y/n): ");
        String input = sc.nextLine();

        // GROUP EXPENSE
        if (input.equals("y") || input.equals("Y")) {
            // Expense.createGroupExpense();
            System.out.println("Group expenses are not currently supported.");
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
            if (Expense.createExpense(amount, currentUser.getUID(), people)) {
                System.out.println("Expense has been successfully created!");
                System.out.println(Data.EXPENSES);
            }
        } else {
            // TODO: Handle this
            System.out.println("Please enter a valid choice.");
        }

    }

    private static void authenticateUser() {
        // TODO: Implement this method
        isLoggedIn = Boolean.TRUE;
    }


    /** View to create new groups
     */
    public static void createGroupView(User currentUser) {
        if (!isLoggedIn) {
            System.out.println("Error: You must be authenticated to create a new group.");
            return;
        }

        String gName;
        List<Person> members = new ArrayList<>();

        System.out.println("Enter name of the group: ");
        gName = sc.nextLine();

        boolean addAnotherMember = false;

        do {
            System.out.println("Enter email of member " + (members.size() + 1) + ": ");
            String memberName = sc.nextLine();
            Person member = new Person(memberName, 0, "n/a");
            members.add(member);

            System.out.println("Would you like to add more members? (y/n)");

            if (sc.nextLine().equals("y")) {
                addAnotherMember = Boolean.TRUE;
            }
            else{
                addAnotherMember = Boolean.FALSE;
            }
        } while (addAnotherMember);

        Group g1 = new Group(
                gName, members, new ArrayList<Expense>(), "N/A");
        currentUser.addGroup(g1);

    }

    /**
     * Returns user's unique identifier through email
     * @param email Email to search user
     * @return UUID of user is user with given email exists in Data.USERS, "0" otherwise
     */
    public static String getUUID(String email) {
        for (Person person: Data.USERS) {
            try {
                User user = (User) person;
                if (user.getEmail().equals(email)) {
                    return user.getUID();
                }
            } catch (Exception ignored) { }
        }

        return "0";
    }
}
