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
    private final RepositoryGateway repositoryGateway;

    public BudgetManager(BudgetRepositoryGateway budgetRepositoryGateway,
                         GroupRepositoryGateway groupRepositoryGateway,
                         ItemRepositoryGateway itemRepositoryGateway,
                         RepositoryGateway repositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.groupRepositoryGateway = groupRepositoryGateway;
        this.itemRepositoryGateway = itemRepositoryGateway;
        this.repositoryGateway = repositoryGateway;
    }

    public boolean create(String GUID, String name, double maxSpend) {
        Group group = this.groupRepositoryGateway.findById(GUID); // TODO: instead of GUID, maybe group name? using findAll() and then loop over to get that group
        if (group == null) {
            return false;
        }
        String BUID = Integer.toString(this.repositoryGateway.getNewBUID());
        Budget budget = new Budget(BUID, name, maxSpend);
        group.addBudget(budget);
        this.repositoryGateway.addBudget(budget);
        this.budgetRepositoryGateway.save(budget);
        this.groupRepositoryGateway.save(group);
        return true;
    }

    public String getBUIDFromName(String name) {
        List<Budget> budgets = repositoryGateway.getBudgets();
        for (Budget budget : budgets) {
            if (budget.getName().equals(name)) {
                return budget.getBUID();
            }
        }
        return null;
    }

    public List<String> getItems(String BUID) {
        return new ArrayList<>(this.budgetRepositoryGateway.findById(BUID).getItems().keySet());
    }

    public String getIUIDFromName(String name) {
        List<Item> items = repositoryGateway.getItems();
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item.getIUID();
            }
        }
        return null;
    }

    public List<Expense> toExpenses(String BUID, Group group, User payee, ExpenseManager expenseManager) {
        Budget budget = this.budgetRepositoryGateway.findById(BUID);
        List<Expense> expenses = new ArrayList<>();
        for (String itemName : budget.getItems().keySet()) {
            Item item = budget.getItem(itemName);
            Expense expense = expenseManager.createExpense(item, group, payee);
            expenses.add(expense);
        }
        return expenses;
    }

    public void addItem(String BUID, String name, double cost, int quantity) { // TODO: Instead of passing in a Budget, maybe pass in just the BUID/name instead?
        Budget budget = this.budgetRepositoryGateway.findById(BUID);
        String IUID = Integer.toString(this.repositoryGateway.getNewIUID());
        Item newItem = new Item(IUID, name, cost, quantity);
        budget.addItem(newItem);
        this.repositoryGateway.addItem(newItem);
        this.itemRepositoryGateway.save(newItem); // TODO: Is a separate item repository needed?
        this.budgetRepositoryGateway.save(budget);
    }

    public boolean changeItemQuantity(String IUID, int newQuantity) {
        List<Budget> budgets = this.budgetRepositoryGateway.findAll();
        for (Budget budget : budgets) {
            Item item = budget.getItem(IUID); // TODO: This uses the getItem(String name) method
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
                budget.removeItem(item.getName());
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
