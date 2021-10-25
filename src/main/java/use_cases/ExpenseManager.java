package use_cases;
import entities.*;
import data.*;

import java.util.List;
import java.util.Objects;

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
     * Creates a new expense and adds it to every user associated with the expense.
     *
     * @param title The title of the expense.
     * @param amount The amount of the expense.
     * @param people List containing emails of users associated with this expense (First email in the list is of payer).
     *
     * @throws NullPointerException If any of the Strings in people is not the email address of any available User.
     */
    public static void createExpense(String title, double amount, List<String> people, User currentUser)
            throws NullPointerException {
        Expense expense = new Expense(title, amount, people);
        Data.expenses.add(expense);

        for (String userEmail: people) {
            Objects.requireNonNull(UserManager.getUser(userEmail)).addExpense(expense);
        }

        // TODO: Fix expense splitting and then use User's addExpense method to update the balance
        currentUser.updateBalance(-amount);
    }

    /**
     * Creates a new expense and adds it to every user in the associated group.
     *
     * @param title The title of the expense.
     * @param amount The amount of the expense.
     * @param groupName The name of the group whose members are to be associated with the expense.
     *
     * @throws NullPointerException If any of the members of the group do not exist. // TODO: Check whether this can occur
     */
    public static boolean createGroupExpense(String title, double amount, String groupName, User currentUser)
            throws NullPointerException {
        for (Group group: Data.groups) {
            if (group.getGroupName().equals(groupName)) {
                createExpense(title, amount, group.getGroupMembers(), currentUser);
                return true;
            }
        }
        return false;
    }

    /**
     * payBill's helper; used when the user wants to pay off the debt of the group. // TODO: Implement payBill?
     * @param payee - The person that pays the bill.
     * @param expenseUID - The unique identifier of expense
     */
    public static void payDebt(Person payee, String expenseUID) {
        Expense expense = ExpenseManager.getExpense(expenseUID);
        assert expense != null;
        expense.settleExpense();

        double amountPaid = expense.getAmount() / expense.numPeople();

        // Get and set the balance of the payee.
        payee.updateBalance(-amountPaid); // remove the money from the payee's account.
        expense.getPayer().updateBalance(amountPaid); // add the money to the bal of the person who paid
    }
}
