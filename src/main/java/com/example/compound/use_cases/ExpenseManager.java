package com.example.compound.use_cases;

import com.example.compound.entities.*;
import com.example.compound.data.*;

/*
This file represents the Expense Class manager. The entity Expense is changed here.
 */
public class ExpenseManager {
    public static Expense getExpense(String expenseUID) {
        try {
            for (Expense expense: Data.expenses) {
                if (expense.getEUID().equals(expenseUID)) {
                    return expense;
                }
            }
        } catch (Exception ignored) { }
        return null;
    }

    /**
     * payBill's helper; used when the user wants to pay off the debt of the group.
     * @param payee - The person that pays the bill.
     * @param expenseUID - The unique identifier of expense
     */
    public static void payDebt(Person payee, String expenseUID, Double amount) {
        Expense expense = ExpenseManager.getExpense(expenseUID);
        assert expense != null;
        expense.settleExpense(payee, amount);

        // Get and set the balance of the payee.
        payee.updateBalance(-amount); // remove the money from the payee's account.
    }


}
