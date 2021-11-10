package entities.budget.use_cases;

import entities.Group;
import entities.budget.entities.Budget;
import entities.group.use_cases.GroupRepositoryGateway;

public class BudgetCreationInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;

    public BudgetCreationInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                    GroupRepositoryGateway groupRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.groupRepositoryGateway = groupRepositoryGateway;
    }

    public boolean create(String groupName, String BUID, String name, String[] categories, double maxSpend, int timeSpan) {
        Group group = new Group(groupName, null, null, null); // TODO: Get group from name
        Budget budget = new Budget(BUID, name, categories, maxSpend, timeSpan);
        return this.budgetRepositoryGateway.createBudget(budget)
                && this.groupRepositoryGateway.addBudget(group, budget); // TODO: Instead of passing in a Group, maybe pass in just the GUID/name instead?
    }
}
