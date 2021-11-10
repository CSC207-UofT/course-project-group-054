package entities.budget.use_cases;

import entities.budget.entities.Budget;

public class BudgetItemRemovingInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final ItemRepositoryGateway itemRepositoryGateway;

    public BudgetItemRemovingInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                      ItemRepositoryGateway itemRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    public boolean removeItem(Budget budget, String IUID) {
        return this.itemRepositoryGateway.deleteItem(IUID)
                && this.budgetRepositoryGateway.removeItem(budget, IUID);
    }
}
