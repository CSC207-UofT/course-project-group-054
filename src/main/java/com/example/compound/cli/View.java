package com.example.compound.cli;
import java.util.*;

import com.example.compound.cli_controllers.InOut;

/**
 This is the View class which handles what the user sees in the command line.
 */
public class View implements InOut {
    public static Scanner sc = new Scanner(System.in);

    /**
     * Return user input as a String.
     * @return the user's input
     */
    public String getInput() {
        return sc.nextLine();
    }

    /**
     * Print the given object.
     * @param s the object to print
     */
    public void sendOutput(Object s) {
        System.out.println(s);
    }

    /**
     * Request the user to enter input for the given attribute and return that input.
     * @param attribute the attribute for which the user is requested to enter input
     * @return the user's input
     */
    public String requestInput(String attribute) {
        System.out.println("Enter " + attribute + ": ");
        return sc.nextLine();
    }

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
            System.out.println("Please select a valid option.\n");
            return getActionView(actions);
        }
        if ((1 <= number) && (number <= actions.length)) {
            return number;
        } else {
            System.out.println("Please select a valid option.\n");
            return getActionView(actions);
        }
    }
}
