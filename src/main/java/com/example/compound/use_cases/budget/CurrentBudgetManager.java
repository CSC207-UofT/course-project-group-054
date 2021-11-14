package com.example.compound.use_cases.budget;

import com.example.compound.entities.Budget;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;

public class CurrentBudgetManager {
    private Budget currentBudget;
    private final BudgetRepositoryGateway budgetRepositoryGateway;

    public CurrentBudgetManager(String BUID, BudgetRepositoryGateway budgetRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.setCurrentBudget(BUID);

    }

    public String getCurrentBudgetUID() {
        return currentBudget.getBUID();
    }

    public void setCurrentBudget(String BUID) {
        this.currentBudget = this.budgetRepositoryGateway.findById(BUID);
    }
}
