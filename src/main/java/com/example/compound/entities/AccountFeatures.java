package com.example.compound.entities;

public interface AccountFeatures {
    /**
     * Generate a unique integer.
     * @return an integer representing the UID (unique integer).
     */
    int generateUUID();

    void addExpense(Expense E);
}
