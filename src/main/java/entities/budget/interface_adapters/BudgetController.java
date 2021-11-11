package entities.budget.interface_adapters;

import entities.budget.use_cases.*;

public class BudgetController {
    private BudgetCreationInteractor budgetCreationInteractor;
    private BudgetItemAddingInteractor budgetItemAddingInteractor;
    private BudgetItemQuantityChangingInteractor budgetItemQuantityChangingInteractor;
    private BudgetItemRemovingInteractor budgetItemRemovingInteractor;
    private BudgetMaxSpendInteractor budgetMaxSpendInteractor;
    private BudgetPercentagesInteractor budgetPercentagesInteractor;
    private BudgetRemovalInteractor budgetRemovalInteractor;

    public BudgetController() {
    }
}
