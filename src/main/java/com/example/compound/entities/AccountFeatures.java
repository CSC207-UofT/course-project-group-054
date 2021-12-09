package com.example.compound.entities;

/**
 * An interface representing the features that distinguish a user with an account from the user without an account.
 */
public interface AccountFeatures {
    /**
     * Add the given expense to this user's list.
     * @param E the expense to add
     */
    void addExpense(Expense E);
}
