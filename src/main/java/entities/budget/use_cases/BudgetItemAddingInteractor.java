package entities.budget.use_cases;

import entities.budget.entities.Budget;
import entities.budget.entities.Item;

public class BudgetItemAddingInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final ItemRepositoryGateway itemRepositoryGateway;

    public BudgetItemAddingInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                      ItemRepositoryGateway itemRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    public boolean addItem(String BUID, Item newItem) { // TODO: Instead of passing in a Budget, maybe pass in just the BUID/name instead?
        Budget budget = this.budgetRepositoryGateway.loadBudget(BUID);
        return this.itemRepositoryGateway.createItem(newItem)
                && this.budgetRepositoryGateway.addItem(budget, newItem);
    }
}
