package entities.budget.use_cases;

import entities.Expense;
import entities.budget.entities.Budget;
import entities.budget.entities.Item;

import java.util.HashMap;
import java.util.List;

public interface BudgetRepositoryGateway {
    Budget loadBudget(String BUID);

    boolean createBudget(Budget budget); // TODO: Create a new data transfer object (see https://piazza.com/class/kt4hlydpsym1bz?cid=843)

    boolean setMaxSpend(Budget budget, double newMaxSpend);

    double getMaxSpend(Budget budget);

    boolean addItem(Budget budget, Item newItem);

    boolean removeItem(Budget budget, String IUID);

    HashMap<String, Double> getPercentages(Budget budget);

    List<Expense> getExpenses(Budget budget);

    boolean removeBudget(String BUID);
}
