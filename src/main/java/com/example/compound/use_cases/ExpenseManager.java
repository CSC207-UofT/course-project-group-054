package com.example.compound.use_cases;

import com.example.compound.entities.*;
import com.example.compound.use_cases.gateways.RepositoryGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A use case class containing functions for managing Expenses.
 */
public class ExpenseManager {
    public final RepositoryGateway repositoryGateway;

    /**
     * Construct a new ExpenseManager with the given parameters.
     * @param repositoryGateway the repository for all objects
     */
    public ExpenseManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }

    /**
     * Creates a new expense and adds it to every user associated with the expense.
     * @param expenseTitle The title of the expense
     * @param amount The amount of expense
     * @param whoPaid a map from people to the amounts those people paid
     * @param whoBorrowed a map from people to the amounts those people borrowed
     * @param userManager the UserManager object that is to add the expense to the Persons
     * @return True, if expense was successfully created and handles. False otherwise.
     */
    public Expense createExpense(String expenseTitle, double amount,
                                 Map<Person, Double> whoPaid,
                                 Map<Person, Double> whoBorrowed,
                                 UserManager userManager) {
        try {
            String EUID = Integer.toString(repositoryGateway.getNewEUID());
            Expense expense = new Expense(EUID, expenseTitle, amount, whoPaid, whoBorrowed);
            repositoryGateway.addExpense(expense);

            ArrayList<String> people = new ArrayList<>();

            ArrayList<Person> tempLst = new ArrayList<>();
            tempLst.addAll(whoPaid.keySet());
            tempLst.addAll(whoBorrowed.keySet());

            int i = 0;
            for (Person p: tempLst) {
                people.add(i, p.getEmail());
                System.out.println(p.getEmail());
                i++;
            }

            for (String userEmail: people) {
                try {
                    Objects.requireNonNull(
                            userManager.getUser(userEmail)).addExpense(expense);
                } catch (Exception ignored) {
                }
            }
            return expense;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Create and return an expense from the given Item.
     * @param item the Item from which an expense is to be created
     * @return the expense created from the given Item
     */
    public Expense createExpense(Item item) {
        HashMap<Person, Double> whoPaid = new HashMap<>();
        HashMap<Person, Double> whoBorrowed = new HashMap<>();

        String EUID = Integer.toString(repositoryGateway.getNewEUID());
        return new Expense(EUID, item.getName(), item.getQuantity() * item.getCost(), whoPaid, whoBorrowed);
    }

    /**
     * Given a UID, return the corresponding expense.
     * @param expenseUID a unique identifier for expenses
     * @return the expense corresponding to the given UID
     */
    public Expense getExpense(String expenseUID) {
        try {
            for (Expense expense: repositoryGateway.getExpenses()) {
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
     * @param amount the amount of the expense
     * @param borrowed whether money was borrowed (true) or paid (false)
     */
    public void payDebt(Person payee, String expenseUID, Double amount, boolean borrowed) {
        Expense expense = getExpense(expenseUID);
        assert expense != null;
        if (borrowed) {
            expense.settleExpenseBorrowed(payee, amount);
        } else {
            expense.settleExpenseLent(payee, amount);
        }

        // Get and set the balance of the payee.
        payee.updateBalance(-amount); // remove the money from the payee's account.
    }
}
