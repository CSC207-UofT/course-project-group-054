package com.example.compound.use_cases;

import com.example.compound.entities.*;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;
import com.example.compound.use_cases.budget.gateways.ItemRepositoryGateway;
import com.example.compound.use_cases.group.GroupRepositoryGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BudgetManager {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;
    private final ItemRepositoryGateway itemRepositoryGateway;

    public BudgetManager(BudgetRepositoryGateway budgetRepositoryGateway,
                         GroupRepositoryGateway groupRepositoryGateway,
                         ItemRepositoryGateway itemRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.groupRepositoryGateway = groupRepositoryGateway;
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    public boolean create(String GUID, String BUID, String name, String[] categories, double maxSpend, int timeSpan) {
        Group group = this.groupRepositoryGateway.findById(GUID); // TODO: instead of GUID, maybe group name? using findAll() and then loop over to get that group
        if (group == null) {
            return false;
        }
        Budget budget = new Budget(BUID, name, categories, maxSpend, timeSpan);
        group.addBudget(budget);
        this.budgetRepositoryGateway.save(budget);
        this.groupRepositoryGateway.save(group);
        return true;
    }

    public List<Expense> toExpenses(String BUID, Group group, User payee, ExpenseManager expenseManager) {
        Budget budget = this.budgetRepositoryGateway.findById(BUID);
        List<Expense> expenses = new ArrayList<>();
        for (String category : budget.getCategories()) {
            for (String itemName : budget.getItemsOfCategory(category).keySet()) {
                Item item = budget.getItemsOfCategory(category).get(itemName);
                Expense expense = expenseManager.createExpense(item, group, payee);
                expenses.add(expense);
            }
        }
        return expenses;
    }

    public void addItem(String BUID, String IUID, String category, String name, double cost, int quantity) { // TODO: Instead of passing in a Budget, maybe pass in just the BUID/name instead?
        Budget budget = this.budgetRepositoryGateway.findById(BUID);
        Item newItem = new Item(IUID, category, name, cost, quantity);
        budget.addItem(category, newItem);
        this.itemRepositoryGateway.save(newItem); // TODO: Is a separate item repository needed?
        this.budgetRepositoryGateway.save(budget);
    }

    public boolean changeItemQuantity(String IUID, int newQuantity) {
        List<Budget> budgets = this.budgetRepositoryGateway.findAll();
        for (Budget budget : budgets) {
            Item item = budget.getItem(IUID);
            if (item != null) {
                item.setQuantity(newQuantity);
                this.itemRepositoryGateway.save(item);
            }
            return true;
        }
        return false;
    }

    public boolean removeItem(String IUID) {
        List<Budget> budgets = this.budgetRepositoryGateway.findAll();
        for (Budget budget : budgets) {
            Item item = budget.getItem(IUID);
            if (item != null) {
                budget.removeItem(item.getCategory(), item.getName());
                this.itemRepositoryGateway.deleteById(IUID);
                this.budgetRepositoryGateway.save(budget);
            }
            return true;
        }
        return false;
    }

    public double getMaxSpend(String BUID) {
        Budget budget = this.budgetRepositoryGateway.findById(BUID);
        return budget.getMaxSpend();
    }

    public void setMaxSpend(String BUID, double newMaxSpend) {
        Budget budget = this.budgetRepositoryGateway.findById(BUID);
        budget.setMaxSpend(newMaxSpend);
        this.budgetRepositoryGateway.save(budget);
    }

    public HashMap<String, Double> getPercentages(String BUID) {
        Budget budget = this.budgetRepositoryGateway.findById(BUID);
        return budget.getPercentages();
    }

    public boolean remove(String GUID, String BUID) { // TODO: Instead of passing in the GUID, maybe pass in the name instead?
        Group group = this.groupRepositoryGateway.findById(GUID); // TODO: What if GUID is invalid?
        boolean removed = group.removeBudget(BUID);
        if (!removed) {
            return false;
        }
        this.budgetRepositoryGateway.deleteById(BUID); // TODO: What if BUID is invalid?
        this.groupRepositoryGateway.save(group);
        return true;
    }
}
