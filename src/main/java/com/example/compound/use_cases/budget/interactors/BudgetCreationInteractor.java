package com.example.compound.use_cases.budget.interactors;

import com.example.compound.entities.Group;
import com.example.compound.entities.Budget;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;
import com.example.compound.use_cases.group.GroupRepositoryGateway;

public class BudgetCreationInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;

    public BudgetCreationInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                    GroupRepositoryGateway groupRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.groupRepositoryGateway = groupRepositoryGateway;
    }

    public boolean create(String GUID, String BUID, String name, double maxSpend) {
        Group group = this.groupRepositoryGateway.findById(GUID); // TODO: instead of GUID, maybe group name? using findAll() and then loop over to get that group
        if (group == null) {
            return false;
        }
        Budget budget = new Budget(BUID, name, maxSpend);
        group.addBudget(budget);
        this.budgetRepositoryGateway.save(budget);
        this.groupRepositoryGateway.save(group);
        return true;
    }
}
