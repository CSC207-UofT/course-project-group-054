package com.example.compound.use_cases.group;

import com.example.compound.entities.Group;
import com.example.compound.entities.Budget;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;

import java.util.ArrayList;
import java.util.List;

public class GroupGetBudgetNameListInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;

    public GroupGetBudgetNameListInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                            GroupRepositoryGateway groupRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.groupRepositoryGateway = groupRepositoryGateway;
    }

    public List<String> getBudgetNameList(String GUID) {
        Group group = this.groupRepositoryGateway.findById(GUID);
        List<Budget> budgets = group.getBudgets();
        List<String> budgetNames = new ArrayList<>();
        for (Budget budget : budgets) {
            budgetNames.add(budget.getName());
        }
        return budgetNames;
    }
}