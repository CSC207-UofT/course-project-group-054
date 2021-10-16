/*
 * Below is the Expense class which represents the origin of our program.
 */
package entities;


import java.time.*;
import java.util.*;

import controller.Controller;
import data.*;

public class Expense {

    private String EUID;
    private boolean isGroupExpense;

    private String title;
    private String description;
    private double amount;

    private String payerUUID;
    private List<String> people;

    private LocalDateTime time;
    private boolean isSettled;

    public void settleExpense() {
        this.isSettled = Boolean.TRUE;
    }

    public int numPeople() {
        return this.people.size();
    }


    /**
     * Construct Expense, with title, cost, payers, note, and current time
     * @param title the title of the Expense
     * @param amount the cost of the Expense
     * @param payerUUID the unique identifier of payer
     * @param description the description of the Expense
     */
    public Expense(String title, double amount, String payerUUID, List<String> people, String description) {
        this.title = title;
        this.amount = amount;
        this.time = LocalDateTime.now();
        this.payerUUID = payerUUID;
        this.description = description;
        this.isGroupExpense = false; // TODO: Change this so it is not fixed
        this.EUID = Integer.toString(Data.expenses.size() + 1);
        this.people = people;
    }

    public String getTitle(){return this.title;}

    public double getAmount(){return this.amount;}

    public LocalDateTime getTime(){return this.time;}

    public User getPayer(){
        // Data.USERS.stream().findFirst();
        // return this.payer;
        return new User("Rohan", 100, "");
    }

    public String getDescription(){return this.description;}

    // TODO

    /**
     * Creates a new expense and adds it to every user associated with the expense.
     * @param amount The amount of expense.
     * @param people List containing emails of users associated with this expense (First email in the list is of payer).
     *
     * @return True, if expense was successfully created and handles. False otherwise.
     */
    public static boolean createExpense(double amount, String payerUUID, List<String> people) {
        try {
            Expense expense = new Expense("Expense Title", amount, payerUUID, people, "Description");
            Data.expenses.add(expense);
            // TODO Search for user through UUID in Data.USERS and add expense in that user.expenses

            System.out.println("People: " + expense.people);

            for (String userEmail: people) {
                try {
                    System.out.println(Controller.getUser(people.get(0)).getName());
                    Objects.requireNonNull(Controller.getUser(userEmail)).expenses.add(expense.EUID);
                } catch (Exception ignored) { }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean createGroupExpense(String title, double amount, String payerUUID, Group group) {
        try {
            Expense expense = new Expense(title, amount, payerUUID, group.getGroupMembers(), "");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please enter a valid group name");
            return false;
        }
        return true;
    }

    public void printExpense() {
        HashMap<String, String> lines = new HashMap<>();
        lines.put("Amount:", "" + this.amount);
        lines.put("Paid by:", "DUMMY");

        // TODO: Print all details about this expense on the console.
        // lines.forEach();
    }

    public String getEUID() {
        return this.EUID;
    }


    @Override
    public String toString() {
        return this.EUID + "     " + this.title + "     " + this.numPeople();
    }
}
