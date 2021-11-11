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

    public void addExpensesFromBudget(String GUID, String BUID) { // TODO: Instead of passing in a Group, maybe pass in just the GUID/name instead?
        Group group = this.groupRepositoryGateway.findById(GUID);
        Budget budget = group.getBudget(BUID);
        List<Expense> budgetExpenses = budget.toExpenses(group);
        for (Expense expense : budgetExpenses) {
            group.addExpense(expense);
        }
        this.groupRepositoryGateway.save(group);
    }
}
