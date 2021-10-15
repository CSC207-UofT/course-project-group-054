package Controller;

import java.sql.SQLOutput;
import java.util.*;

import Use_Cases.*;
import Entities.*;

public class Controller {

    private static User currentUser;

    // TODO: Replace the following dummy variable for app name
    private static String appName = "[APP NAME]";
    public static String[] mainMenuOptions = {"Sign in to my account", "Create a new account"};

    private static Scanner sc = new Scanner(System.in);

    public static void signUp() {
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
    }


    public static void main(String[] args) {
        boolean status = true;
        System.out.println("Welcome to " + appName);
        for (int i = 1; i <= mainMenuOptions.length; i++) {
            System.out.println(i + ") " + mainMenuOptions[i - 1]);
        }
        System.out.println("\n");
        signUp();

        while (status) {
            System.out.println("Please enter the number for the actions below:\n" +
                    "1. Add an expense\n" +
                    "2. Manage groups\n" +
                    "3. Check balance\n" +
                    "4. Update Profile\n" +
                    "5. Log out");
            String input = sc.nextLine();
            if(input.equals("1")){
                GroupManager.create_temp();
            }
            else if(input.equals("2")){
                GroupManager.show_group(currentUser);

            }
            else if(input.equals("3")){
                UserManager.show_balance(currentUser);
            }
            else if (input.equals("4")){
                UserManager.updateProfile(currentUser);
            }
            else if (input.equals("5")){
                System.out.println("Goodbye. Have a nice day!");
                status = false;
            }
            else{

            }


            break;
        }
    }

}
