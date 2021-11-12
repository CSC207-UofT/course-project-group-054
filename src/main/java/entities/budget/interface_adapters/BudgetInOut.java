package entities.budget.interface_adapters;

import java.util.List;

public interface BudgetInOut { // TODO: Make this and current InOut (rename to DashboardInOut?) extend new InOut interface?
    String getInput();

    void sendOutput(Object s);

    int getActionView(String[] actions);

    void showBudgets(List<String> budgets);

    String getNewBudgetName();

    double getBudgetMaxSpend();

    String getNewItemName();

    double getNewItemCost();

    int getNewItemQuantity();
}
