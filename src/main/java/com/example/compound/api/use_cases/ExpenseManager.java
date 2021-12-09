package com.example.compound.api.use_cases;

import com.example.compound.api.entities.Expense;
import com.example.compound.api.entities.User;

import java.util.Map;

/*
This file represents the Expense Class manager. The entity Expense is changed here.
 */
public class ExpenseManager {
    public ExpenseManager() {}
//    /**
//     * payBill's helper; used when the user wants to pay off the debt of the group.
//     * @param payee - The person that pays the bill.
//     * @param expenseUID - The unique identifier of expense
//     */
//    public void payDebt(Expense expense, Person payee, String expenseUID, Double amount, boolean borrowed) {
//        expense.settleExpense(payee, amount, borrowed);
//        // Get and set the balance of the payee.
//        payee.updateBalance(-amount); // remove the money from the payee's account.
//    }


    // TODO add javadoc
    public void settleExpense(Expense expense, User user, double amountPaid, boolean borrowed) {
        expense.setAmount(expense.getAmount() - amountPaid);

//        if (borrowed){
//            Double amount = this.whoBorrowed.get(p);
//            this.whoBorrowed.replace(p, amount - amountPaid);
//        }
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

//    public void updateBalances(Map<com.example.compound.api.entities.Person, Double> whoPaid){
//        for(Person key : whoPaid.keySet()){
//            key.balance -= whoPaid.get(key);
//        }
//    }
}
