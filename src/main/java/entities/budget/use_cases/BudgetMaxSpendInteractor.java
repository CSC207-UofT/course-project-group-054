package entities.budget.use_cases;

import entities.budget.entities.Budget;

public class BudgetMaxSpendInteractor { // TODO: Should there be two separate interactors?
    private final BudgetRepositoryGateway budgetRepositoryGateway;

    public BudgetMaxSpendInteractor(BudgetRepositoryGateway budgetRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
    }

    public double getMaxSpend(Budget budget) {
        return this.budgetRepositoryGateway.getMaxSpend(budget);
    }

    public boolean setMaxSpend(Budget budget, double newMaxSpend) {
        return this.budgetRepositoryGateway.setMaxSpend(budget, newMaxSpend);
    }
}
