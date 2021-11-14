package com.example.compound.use_cases.budget.interactors;

import com.example.compound.entities.Budget;
import com.example.compound.entities.Item;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;
import com.example.compound.use_cases.budget.gateways.ItemRepositoryGateway;

import java.util.List;

public class BudgetItemQuantityChangingInteractor {
    private final ItemRepositoryGateway itemRepositoryGateway;
    private final BudgetRepositoryGateway budgetRepositoryGateway;

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
