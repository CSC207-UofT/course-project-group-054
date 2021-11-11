package entities.budget.use_cases.interactors;

import entities.budget.entities.Budget;
import entities.budget.use_cases.gateways.BudgetRepositoryGateway;

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
