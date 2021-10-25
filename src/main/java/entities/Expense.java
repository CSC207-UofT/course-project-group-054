package entities;

import java.util.*;

import data.*;

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
