package com.example.compound.use_cases.budget.interactors;

import com.example.compound.entities.Budget;
import com.example.compound.entities.Item;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;
import com.example.compound.use_cases.budget.gateways.ItemRepositoryGateway;

public class BudgetItemAddingInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final ItemRepositoryGateway itemRepositoryGateway;

    public BudgetItemAddingInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                      ItemRepositoryGateway itemRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    public void addItem(String BUID, String IUID, String category, String name, double cost, int quantity) { // TODO: Instead of passing in a Budget, maybe pass in just the BUID/name instead?
        Budget budget = this.budgetRepositoryGateway.findById(BUID);
        Item newItem = new Item(IUID, category, name, cost, quantity);
        budget.addItem(category, newItem);
        this.itemRepositoryGateway.save(newItem); // TODO: Is a separate item repository needed?
        this.budgetRepositoryGateway.save(budget);
    }
}
