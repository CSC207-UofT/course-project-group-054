package com.example.compound.entities;

import java.util.*;

import com.example.compound.data.*;
import com.example.compound.use_cases.UserManager;

/*
 * Below is the Expense class which represents the origin of our program.
 */

public class Expense {

    private final String EUID;

    private final String title;
    private double amount;

    private final Map<Person, Double> whoPaid;
    private final Map<Person, Double> whoBorrowed;

    /**
     * Construct Expense, with title, cost, payers, note, and current time
     * @param title the title of the Expense
     * @param amount the cost of the Expense
     * @param whoPaid Map of People:AmountPaid
     * @param whoBorrowed Map of People:AmountBorrowed
     */
    //TODO: Implement multiple split types for Phase 2
    public Expense(String title, double amount,
                   Map<Person, Double> whoPaid,
                   Map<Person, Double> whoBorrowed) {
        this.title = title;
        this.amount = amount;
        this.EUID = Integer.toString(Data.getExpenses().size() + 1);
        this.whoPaid = whoPaid;
        this.whoBorrowed = whoBorrowed;
        this.updateBalances(whoPaid);
    }

    public double getAmount(){
        return this.amount;
    }

    /**
     * Creates a new expense and adds it to every user associated with the expense.
     *
     * @param expenseTitle The title of the expense
     * @param amount The amount of expense.
     *
     * @return True, if expense was successfully created and handles. False otherwise.
     */
    public static Expense createExpense(String expenseTitle, double amount,
                                        Map<Person, Double> whoPaid,
                                        Map<Person, Double> whoBorrowed) {
        try {
            Expense expense = new Expense(expenseTitle, amount, whoPaid, whoBorrowed);
            Data.expenses.add(expense);

            ArrayList<String> people = new ArrayList<>();

            ArrayList<Person> tempLst = new ArrayList<>();
            tempLst.addAll(whoPaid.keySet());
            tempLst.addAll(whoBorrowed.keySet());

            int i = 0;
            for (Person p: tempLst) {
                people.add(i, p.email);
                System.out.println(p.email);
                i++;
            }

            for (String userEmail: people) {
                try {
                    Objects.requireNonNull(
                            UserManager.getUser(userEmail)).expenses.add(expense.EUID);
                } catch (Exception ignored) { }
            }
            return expense;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getEUID() {
        return this.EUID;
    }

    public int numPeople() {
        return this.whoPaid.size() + this.whoBorrowed.size();
    }

    @Override
    public String toString() {
        return this.EUID + "     " + this.title + "     " + this.numPeople();
    }

    public void settleExpense(Person p, Double amountPaid) {
        this.amount = this.amount - amountPaid;
        Double amount = this.whoBorrowed.get(p);
        this.whoBorrowed.replace(p, amount - amountPaid);
    }

    public void updateBalances(Map<Person, Double> whoPaid){
        for(Person key : whoPaid.keySet()){
            key.balance -= whoPaid.get(key);
        }
    }
}
