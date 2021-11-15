package com.example.compound.entities;

import java.util.*;

import com.example.compound.data.*;

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
    public Expense(String EUID, String title, double amount,
                   Map<Person, Double> whoPaid,
                   Map<Person, Double> whoBorrowed) {
        this.EUID = EUID;
        this.title = title;
        this.amount = amount;
        this.whoPaid = whoPaid;
        this.whoBorrowed = whoBorrowed;
        this.updateBalances(whoPaid);
    }

    public double getAmount(){
        return this.amount;
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

    public void settleExpense(Person p, Double amountPaid, boolean borrowed) {
        this.amount = this.amount - amountPaid;

        if (borrowed){
            Double amount = this.whoBorrowed.get(p);
            this.whoBorrowed.replace(p, amount - amountPaid);
        }
//        else{
//            Double amount = this.whoPaid.get(p);
//            if (amount - amountPaid < 0){
//                System.out.println("You've entered too much!");
//            }
//            else{
//                this.whoPaid.replace(p, amount - amountPaid);
//            }
//        }

    }

    public void updateBalances(Map<Person, Double> whoPaid){
        for(Person key : whoPaid.keySet()){
            key.balance -= whoPaid.get(key);
        }
    }
}
