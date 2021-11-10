package entities.group.use_cases;

import entities.Expense;
import entities.Group;
import entities.budget.entities.Budget;
import entities.budget.use_cases.BudgetRepositoryGateway;

import java.util.List;

public class GroupAddingExpensesFromBudgetInteractor { // TODO: Should this be called something else?
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;

    public GroupAddingExpensesFromBudgetInteractor(BudgetRepositoryGateway budgetRepositoryGateway,
                                                   GroupRepositoryGateway groupRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
        this.groupRepositoryGateway = groupRepositoryGateway;
    }

    public boolean addExpensesFromBudget(Group group, Budget budget) { // TODO: Instead of passing in a Group, maybe pass in just the GUID/name instead?
        List<Expense> budgetExpenses = this.budgetRepositoryGateway.getExpenses(budget);
        return this.groupRepositoryGateway.addExpenses(group, budgetExpenses);
    }
}
