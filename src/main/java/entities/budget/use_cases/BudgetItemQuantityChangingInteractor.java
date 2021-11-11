package entities.budget.use_cases;

import entities.budget.entities.Budget;
import entities.budget.entities.Item;

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
        Budget budget = this.budgetRepositoryGateway.loadBudgetFromIUID(IUID, itemRepositoryGateway);
        Item item;
        try {
            item = Objects.requireNonNull(budget.getItem(IUID)); // TODO: new overloaded method?
        } catch (NullPointerException e) {
            return false;
        }
        return item.setQuantity(newQuantity) && this.itemRepositoryGateway.save(item);
    }
}
