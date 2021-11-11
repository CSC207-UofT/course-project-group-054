package entities.budget.use_cases;

import entities.budget.entities.Budget;

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
