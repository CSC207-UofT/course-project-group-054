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

    public boolean remove(String GUID, String BUID) { // TODO: Instead of passing in the GUID, maybe pass in the name instead?
        Group group = this.groupRepositoryGateway.findById(GUID); // TODO: What if GUID is invalid?
        boolean removed = group.removeBudget(BUID);
        if (!removed) {
            return false;
        }
        this.budgetRepositoryGateway.deleteById(BUID); // TODO: What if BUID is invalid?
        this.groupRepositoryGateway.save(group);
        return true;
    }
}
