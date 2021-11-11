package entities.budget.use_cases;

import entities.budget.entities.Budget;
import entities.budget.entities.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BudgetItemQuantityChangingInteractor {
    private final ItemRepositoryGateway itemRepositoryGateway;
    private BudgetRepositoryGateway budgetRepositoryGateway;

    public BudgetItemQuantityChangingInteractor(ItemRepositoryGateway itemRepositoryGateway,
                                                BudgetRepositoryGateway budgetRepositoryGateway) {
        this.itemRepositoryGateway = itemRepositoryGateway;
        this.budgetRepositoryGateway = budgetRepositoryGateway;
    }

    public boolean changeItemQuantity(String IUID, int newQuantity) {
        List<Budget> budgets = this.budgetRepositoryGateway.findAll();
        for (Budget budget : budgets) {
            Item item = budget.getItem(IUID);
            if (item != null) {
                item.setQuantity(newQuantity);
                this.itemRepositoryGateway.save(item);
            }
            return true;
        }
        return false;
    }
}
