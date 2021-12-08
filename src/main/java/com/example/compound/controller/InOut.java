package com.example.compound.controller;

/**
 * A command-line input and output handler.
 */
public interface InOut {
    /**
     * Return user input as a String.
     * @return the user's input
     */
    String getInput();

    /**
     * Print the given object.
     * @param s the object to print
     */
    void sendOutput(Object s);

    /**
     * Request the user to enter input for the given attribute and return that input.
     * @param attribute the attribute for which the user is requested to enter input
     * @return the user's input
     */
    String requestInput(String attribute);

    /**
     * Given a list of possible options for the user, output the options as a numbered list and request that the user
     * choose one, input their choice as the number corresponding to the chosen option, and return the number.
     *
     * @param options a list of the possible options for the user
     * @return a number between 1 and the length of the given list of options, inclusive, representing the option at
     *         the corresponding position in the given list plus 1
     */
    int getOptionView(String[] options);
}