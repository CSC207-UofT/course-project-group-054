package entities.budget.use_cases.interactors;

import entities.budget.entities.Budget;
import entities.budget.entities.Item;
import entities.budget.use_cases.gateways.BudgetRepositoryGateway;
import entities.budget.use_cases.gateways.ItemRepositoryGateway;

import java.util.List;

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
