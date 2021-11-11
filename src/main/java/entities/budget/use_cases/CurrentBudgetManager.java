package entities.budget.use_cases;

import entities.budget.entities.Budget;
import entities.budget.use_cases.gateways.BudgetRepositoryGateway;

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
