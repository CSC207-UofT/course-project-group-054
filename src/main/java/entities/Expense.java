package entities;

import java.util.*;

import data.*;
import use_cases.UserManager;

/*
 * Below is the Expense class which represents the origin of our program.
 */

public class Expense {

    private final String EUID;

    private final String title;
    private double amount;

    private final List<String> people;

    public int numPeople() {
        return this.people.size();
    }

    /**
     * Construct Expense, with title, cost, payers, note, and current time
     * @param title the title of the Expense
     * @param amount the cost of the Expense
     */
    public Expense(String title, double amount, List<String> people) {
        this.title = title;
        this.amount = amount;
        this.EUID = Integer.toString(Data.expenses.size() + 1);
        this.people = people;
    }

    public double getAmount(){return this.amount;}


    public User getPayer(){
        return new User("Rohan", 100, "");
    }

    /**
     * Creates a new expense and adds it to every user associated with the expense.
     *
     * @param expenseTitle The title of the expense
     * @param amount The amount of expense.
     * @param people List containing emails of users associated with this expense (First email in the list is of payer).
     *
     * @return True, if expense was successfully created and handles. False otherwise.
     */
    public static boolean createExpense(String expenseTitle, double amount, List<String> people) {
        try {
            Expense expense = new Expense(expenseTitle, amount, people);
            Data.expenses.add(expense);
            System.out.println("People: " + expense.people);

            for (String userEmail: people) {
                try {
                    Objects.requireNonNull(UserManager.getUser(userEmail)).expenses.add(expense.EUID);
                } catch (Exception ignored) { }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Creates a new expense and adds it to every user associated with the expense.
     * @param amount The amount of expense.
     * @param title The title of the expense.
     *
     * @return True, if expense was successfully created and handles. False otherwise.
     */
    public static boolean createGroupExpense(String title, double amount, Group group) {
        try {
            Expense expense = new Expense(title, amount, group.getGroupMembers());
            for (String userEmail: group.getGroupMembers()) {
                try {
//                    System.out.println(Objects.requireNonNull(Controller.getUser(people.get(0))).getName());
                    Objects.requireNonNull(UserManager.getUser(userEmail)).expenses.add(expense.EUID);
                } catch (Exception ignored) { }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please enter a valid group name");
            return false;
        }
        return true;
    }

    public String getEUID() {
        return this.EUID;
    }

    @Override
    public String toString() {
        return this.EUID + "     " + this.title + "     " + this.numPeople();
    }

    public void settleExpense() {
        this.amount = 0;
    }
}
