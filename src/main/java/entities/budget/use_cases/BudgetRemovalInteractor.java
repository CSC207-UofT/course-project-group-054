package entities.budget.use_cases;

import entities.Group;
import entities.budget.entities.Budget;
import entities.group.use_cases.GroupRepositoryGateway;

public class BudgetRemovalInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;

    public BudgetRemovalInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                   GroupRepositoryGateway groupRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.groupRepositoryGateway = groupRepositoryGateway;
    }

    public boolean remove(Group group, String BUID) { // TODO: Instead of passing in a Group, maybe pass in just the GUID/name instead?
        return this.budgetRepositoryGateway.removeBudget(BUID)
                && this.groupRepositoryGateway.removeBudget(group, BUID);
    }
}
