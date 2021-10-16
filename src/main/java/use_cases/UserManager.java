package use_cases;

import data.*;
import entities.*;

public class UserManager {

    public static double show_balance(Person p){
        return p.getBalance();
    }

    public static void updateProfile(Person p) {

    }

    public boolean createExpense() {
        return true;
    }

    public static StringBuilder getExpenses(User user) {
        StringBuilder lst = new StringBuilder("Recent expenses:\n");

        for (String expenseUID: user.expenses) {
            try {
                for (Expense expense: Data.expenses) {
                    if (expense.getEUID().equals(expenseUID)) {
                        lst.append(expense);
                    }
                }
                return lst;
            } catch (Exception ignored) { }
        }

        return new StringBuilder("You don't have any expenses now.");
    }
}
