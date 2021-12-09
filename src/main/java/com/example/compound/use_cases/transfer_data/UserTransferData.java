package com.example.compound.use_cases.transfer_data;

import com.example.compound.entities.Expense;

import java.util.ArrayList;
import java.util.List;

public class UserTransferData {
    private final String username;
    private final List<String> expenses;
    private String password;
    /**
     * Construct a new User with the given UID, name, balance, email, and password.
     * @param name     the User's name
     * @param email    the User's email used to contact them
     * @param username the User's username
     * @param balance  the User's balance (the amount owed)
     * @param password the User's password
     */
    public UserTransferData(String name, String email, String username, double balance, String password) {
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
    }
    /**
     * Return this user's username.
     * @return this user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return this user's list of expenses.
     * @return this user's list of expenses
     */
    public List<String> getExpenses() {
        return expenses;
    }

    /**
     * Return this user's password.
     * @return this user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set this user's password to the given value.
     * @param password this user's new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Add an expense to this user's list.
     * @param E the expense to add
     */
    public void addExpense(Expense E) {
        this.expenses.add(E.getEUID());
    }
}
