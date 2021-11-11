package entities.budget.use_cases;

import entities.budget.entities.Budget;
import entities.budget.entities.Item;

import java.util.List;

public class BudgetItemRemovingInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final ItemRepositoryGateway itemRepositoryGateway;

    public BudgetItemRemovingInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                      ItemRepositoryGateway itemRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    public boolean removeItem(String IUID) {
        List<Budget> budgets = this.budgetRepositoryGateway.findAll();
        for (Budget budget : budgets) {
            Item item = budget.getItem(IUID);
            if (item != null) {
                budget.removeItem(item.getCategory(), item.getName());
                this.itemRepositoryGateway.deleteById(IUID);
                this.budgetRepositoryGateway.save(budget);
            }
            return true;
        }
        return false;
    }
}
