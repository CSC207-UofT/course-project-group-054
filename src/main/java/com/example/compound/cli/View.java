package com.example.compound.cli;
import java.util.*;

import com.example.compound.controller.InOut;

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
     * Given a list of possible options for the user, output the options as a numbered list and request that the user
     * choose one, input their choice as the number corresponding to the chosen option, and return the number.
     *
     * @param options a list of the possible options for the user
     * @return a number between 1 and the length of the given list of options, inclusive, representing the option at
     *         the corresponding position in the given list plus 1
     */
    public int getOptionView(String[] options) {
        System.out.println("Please enter the number for one of the options below:");
        for (int i = 1; i <= options.length; i++) {
            System.out.println(i + ". " + options[i - 1]);
        }
        String input = sc.nextLine();
        int number;
        System.out.println();
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please select a valid option.\n");
            return getOptionView(options);
        }
        if ((1 <= number) && (number <= options.length)) {
            return number;
        } else {
            System.out.println("Please select a valid option.\n");
            return getOptionView(options);
        }
    }
}
