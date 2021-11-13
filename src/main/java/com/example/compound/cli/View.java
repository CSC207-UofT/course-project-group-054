package com.example.compound.cli;
import java.util.*;

import com.example.compound.controller.InOut;

/*
This is the View class which handles what the user sees in the command line.
 */
public class View implements InOut {
    public static Scanner sc = new Scanner(System.in);

    public String getInput() {
        return sc.nextLine();
    }

    public void sendOutput(Object s) {
        System.out.println(s);
    }

    /**
     * Welcome View
     */
    public void welcome(String appName) {
        System.out.println("Welcome to " + appName);
    }

    /**
     * Request the user to input an email address and return that email address.
     */
    public String requestInput(String attribute) {
        System.out.println("Enter " + attribute + ": ");
        return sc.nextLine();
    }

    public void outputLoginSuccessView(String name) {
        System.out.println("Welcome back, " + name + "!");
    }

    public void outputLoginFailureView() {
        System.out.println("ERROR: There was a problem logging you in. Please try again.");
    }

//    /**
//     * Sign up View
//     */
//    public void signUpView() {
//        String[] outputs = {"Full Name (*): ", "Email (*): ", "Phone: "};
//        List<String> inputs = new ArrayList<>();
//
//        System.out.println("Please enter the following information and press enter. Fields marked (*) are required.");
//        for (String output: outputs) {
//            System.out.println(output);
//            String input = sc.nextLine();
//            inputs.add(input);
//        }
//
//        Data.addUser(UserManager.createUser(inputs.get(0), 0, inputs.get(1)));
//
//
//    }

    /**
     * Given a list of possible actions that the user can take, output the actions as a numbered list and request that
     * the user choose one, input their choice of action as the number corresponding to the chosen action, and return
     * the number.
     *
     * @param actions a list of the possible actions the user can take
     * @return a number between 1 and the length of the given list of actions, inclusive, representing the action at
     *         the corresponding position in the given list
     */
    public int getActionView(String[] actions) {
        System.out.println("Please enter the number for one of the options below:");
        for (int i = 1; i <= actions.length; i++) {
            System.out.println(i + ". " + actions[i - 1]);
        }
        String input = sc.nextLine();
        int number;
        System.out.println();
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please select a valid option.");
            return getActionView(actions);
        }
        if ((1 <= number) && (number <= actions.length)) {
            return number;
        } else {
            System.out.println("Please select a valid option.");
            return getActionView(actions);
        }
    }

    public void outputCreateGroupAuthenticationFailure() {
        System.out.println("Error: You must be authenticated to create a new group.");
    }
}
