package entities.budget.use_cases.interactors;

import entities.Group;
import entities.budget.entities.Budget;
import entities.budget.use_cases.gateways.BudgetRepositoryGateway;
import entities.group.use_cases.GroupRepositoryGateway;

public class BudgetCreationInteractor {
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;

    public BudgetCreationInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                    GroupRepositoryGateway groupRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.groupRepositoryGateway = groupRepositoryGateway;
    }

    public boolean create(String GUID, String BUID, String name, String[] categories, double maxSpend, int timeSpan) {
        Group group = this.groupRepositoryGateway.findById(GUID); // TODO: instead of GUID, maybe group name? using findAll() and then loop over to get that group
        if (group == null) {
            return false;
        }
        Budget budget = new Budget(BUID, name, categories, maxSpend, timeSpan);
        group.addBudget(budget);
        this.budgetRepositoryGateway.save(budget);
        this.groupRepositoryGateway.save(group);
        return true;
    }
}
