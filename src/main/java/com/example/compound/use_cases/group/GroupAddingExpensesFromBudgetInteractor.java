package com.example.compound.use_cases.group;

import com.example.compound.entities.Expense;
import com.example.compound.entities.Group;
import com.example.compound.entities.Budget;
import com.example.compound.entities.User;
import com.example.compound.use_cases.BudgetManager;
import com.example.compound.use_cases.ExpenseManager;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;

import java.util.List;

public class GroupAddingExpensesFromBudgetInteractor { // TODO: Should this be called something else?
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;

    public GroupAddingExpensesFromBudgetInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                                   GroupRepositoryGateway groupRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.groupRepositoryGateway = groupRepositoryGateway;
    }

    public void addExpensesFromBudget(String GUID, String BUID, BudgetManager budgetManager,
                                      ExpenseManager expenseManager) { // TODO: Instead of passing in a Group, maybe pass in just the GUID/name instead?
        Group group = this.groupRepositoryGateway.findById(GUID);
        Budget budget = group.getBudget(BUID);
        List<Expense> budgetExpenses = budgetManager.toExpenses(BUID, group, expenseManager);
        for (Expense expense : budgetExpenses) {
            group.addExpense(expense);
        }
        this.groupRepositoryGateway.save(group);
    }
}
