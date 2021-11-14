package com.example.compound.use_cases.budget.interactors;

import com.example.compound.entities.Budget;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;

import java.util.HashMap;

public class BudgetPercentagesInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;

    public BudgetPercentagesInteractor(BudgetRepositoryGateway budgetRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
    }

    public HashMap<String, Double> getPercentages(String BUID) {
        Budget budget = this.budgetRepositoryGateway.findById(BUID);
        return budget.getPercentages();
    }
}
