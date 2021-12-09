/*
  This file represents the TransferDataClass for the entity Expense.
  This class is used for moving the data to the database.
 */
package com.example.compound.use_cases.transfer_data;

import com.example.compound.entities.Person;

import java.util.Map;

public class ExpenseTransferData {
    private String EUID;
    private String title;
    private double amount;
    private Map<Person, Double> whoPaid;
    private Map<Person, Double> whoBorrowed;

    /**
     * Default Constructor for ExpenseTransferData Class.
     * @param euid The EUID of the Expense
     * @param title The Title of the Expense
     * @param amount The Amount (Cost) of the Expense
     * @param whoPaid A mapping of who Paid for the expense to the amount they paid.
     * @param whoBorrowed A mapping of who Borrowed to the amount they borrowed.
     */
    public ExpenseTransferData(String euid, String title,
                               double amount, Map<Person, Double> whoPaid,
                               Map<Person, Double> whoBorrowed) {
        this.EUID = euid;
        this.title = title;
        this.amount = amount;
        this.whoPaid = whoPaid;
        this.whoBorrowed = whoBorrowed;
    }

    /**
     * An alternative constructor for the ExpenseTransferData Class.
     * @param title The Title of the Expense
     * @param amount The Amount (Cost) of the Expense
     * @param whoPaid A mapping of who Paid for the expense to the amount they paid.
     * @param whoBorrowed A mapping of who Borrowed to the amount they borrowed.
     */
    public ExpenseTransferData(String title, double amount, Map<Person, Double> whoPaid,
                               Map<Person, Double> whoBorrowed){
        this.title = title;
        this.amount = amount;
        this.whoPaid = whoPaid;
        this.whoBorrowed = whoBorrowed;
    }

    /**
     * Getter for Title of Expense
     * @return the title of the Expense
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the hashmap of people who paid an Expense
     * @return the hashmap whoPaid (Person who paid: Amount Paid)
     */
    public Map<Person, Double> getWhoPaid() {
        return whoPaid;
    }

    /**
     * Getter for the hashmap of people who borrwed for an Expense
     * @return the hashmap whoPaid (Person who borrowed: Amount Borrowed)
     */
    public Map<Person, Double> getWhoBorrowed() {
        return whoBorrowed;
    }

    /**
     * Getter for Amount (cost) of Expense
     * @return the cost of the Expense
     */
    public double getAmount(){
        return this.amount;
    }

    /**
     * Getter for EUID of Expense
     * @return the EUID of the Expense
     */
    public String getEUID() {
        return this.EUID;
    }

    /**
     * Getter for Title of Expense
     * @return the title of the Expense
     */
    public int numPeople() {
        return this.whoPaid.size() + this.whoBorrowed.size();
    }

    /**
     * Return the string interpretation of the object
     * @return string interpretation of the object.
     */
    @Override
    public String toString() {
        return this.EUID + "     " + this.title + "     " + this.numPeople() + "    " + this.amount; }


}
