package view;
import java.util.*;
import controller.*;
import data.*;
import entities.*;
import use_cases.ExpenseManager;
import use_cases.UserManager;


public class View {
    // TODO Transfer all the views from Controller class here
    public static Scanner sc = new Scanner(System.in);

    /**
     * Menu View
     */

    public static String[] mainMenuOptions = {"Sign in to my account", "Create a new account", "Close app"};

    public static void menuView() {
        System.out.println("Welcome to " + Controller.appName);
        for (int i = 1; i <= mainMenuOptions.length; i++) {
            System.out.println(i + ") " + mainMenuOptions[i - 1]);
        }
        String menuInput = sc.nextLine();
        switch (menuInput) {
            case "1" -> loginView();
            case "2" -> signUpView();
            case "3" -> System.exit(1);
            default -> {
                System.out.println("Please enter a valid option.");
            }
        }
    }


    /**
     * Login View
     */
    public static void loginView() {
        // TODO: Implement this method
        System.out.println("Enter your email: ");
        String email = sc.nextLine();

        User user = Controller.getUser(email);
        if (user != null) {
            Controller.authenticateUser(user);
        } else {
            System.out.println("ERROR: There was a problem logging you in. Please try again.");
        }
    }

    /**
     * Login View
     */
    public static void signUpView() {
        String[] outputs = {"Full Name (*): ", "Email (*): ", "Phone: "};
        List<String> inputs = new ArrayList<>();

        System.out.println("Please enter the following information and press enter. Fields marked (*) are required.");
        for (String output: outputs) {
            System.out.println(output);
            String input = sc.nextLine();
            inputs.add(input);
        }

        Data.users.add(new User(inputs.get(0),0, inputs.get(1)));

        System.out.println("Thanks for signing up, " + inputs.get(0) + "! You can now log in.");
    }

    /**
     * Dashboard View
     */
    public static void dashboardView() {
        while (Controller.getUserStatus()) {
            System.out.println("""
                    Please enter the number for the actions below:
                    1. Add an expense
                    2. Show groups
                    3. Check balance
                    4. Update Profile [Coming soon]
                    5. Create a new group
                    6. View expenses
                    7. Log out""");
            String input = sc.nextLine();
            switch (input) {
//                case "1" -> GroupManager.create_temp();
                case "1" -> Controller.createExpenseView();
                case "2" -> {
                    StringBuilder lst = ExpenseManager.show_group(Controller.getCurrentUser());
                    System.out.println(lst);
                }
                case "3" -> System.out.println("Your balance is: $" + Controller.getCurrentUser().getBalance());
                case "4" -> UserManager.updateProfile(Controller.getCurrentUser());
                case "5" -> View.createGroupView();
                case "6" -> System.out.println(UserManager.getExpenses(Controller.getCurrentUser()));
                case "7" -> {
                    Controller.logoutUser();
                    System.out.println("Goodbye. Have a nice day!");
                }
                default -> {
                    System.out.println("Please select a valid option.");
                }
            }
        }
    }

    /**
     * Create Group View
     * - View to create new groups
     * @returns 0: if user is not authenticated, the view doesn't allow new group to be created.
     */
    public static int createGroupView() {
        if (!Controller.getUserStatus()) {
            System.out.println("Error: You must be authenticated to create a new group.");
            return 0;
        }


        String gName;
        List<String> members = new ArrayList<>();
        members.add(Controller.getCurrentUser().getEmail());


        //
        System.out.println("Enter name of the group: ");
        gName = sc.nextLine();

        boolean addAnotherMember = false;

        System.out.println("ADD GROUP MEMBERS:");
        System.out.println("You will now be asked to add group members. " +
                "Press enter if you don't want to add any member");

        do {
            System.out.println("Enter email of member " + members.size() + ": ");
            String member = sc.nextLine();
            if (member.equals("")) { continue; }
            members.add(member);

            System.out.println("Would you like to add more members? (y/n)");

            if (sc.nextLine().equals("y")) {
                addAnotherMember = Boolean.TRUE;
            } else {
                addAnotherMember = Boolean.FALSE;
            }
        } while (addAnotherMember);

        Group g1 = new Group(
                gName, members, new ArrayList<Expense>(), "Edit group description in Manage Group."
        );

        Data.groups.add(g1);

        /* For testing the code */
        System.out.println(Data.groups);
        System.out.println(Data.groups.get(1).getGroupName());
        /* */

        return 1;
    }

}
