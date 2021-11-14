package com.example.compound.use_cases.budget.interactors;

import com.example.compound.entities.Budget;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;

public class BudgetMaxSpendInteractor { // TODO: Should there be two separate interactors?
    private final BudgetRepositoryGateway budgetRepositoryGateway;

    public BudgetMaxSpendInteractor(BudgetRepositoryGateway budgetRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
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
}
