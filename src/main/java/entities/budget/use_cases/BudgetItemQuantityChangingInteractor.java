package entities.budget.use_cases;

import entities.budget.entities.Item;

public class BudgetItemQuantityChangingInteractor {
    private final ItemRepositoryGateway itemRepositoryGateway;

    public BudgetItemQuantityChangingInteractor(ItemRepositoryGateway itemRepositoryGateway) {
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    public boolean changeItemQuantity(Item item, int newQuantity) {
        return this.itemRepositoryGateway.changeQuantity(item, newQuantity);
    }
}
