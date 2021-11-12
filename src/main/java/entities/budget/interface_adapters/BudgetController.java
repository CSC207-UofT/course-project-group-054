package entities.budget.interface_adapters;

import entities.budget.use_cases.CurrentBudgetManager;
import entities.budget.use_cases.gateways.BudgetRepositoryGateway;
import entities.budget.use_cases.gateways.ItemRepositoryGateway;
import entities.budget.use_cases.interactors.*;
import entities.group.use_cases.GroupGetBudgetNameListInteractor;
import entities.group.use_cases.GroupRepositoryGateway;

import java.util.List;

public class BudgetController {
    private boolean isInBudgetSelection;
    private String GUID;
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;
    private final ItemRepositoryGateway itemRepositoryGateway;
    private final String[] selectionActions = {
            "Select a budget",
            "Create a new budget",
            "Exit"
    };
    private final String[] budgetActions = {
            "Add an item",
            "Change the quantity of an item",
            "Remove an item",
            "Change the spending limit",
            "Convert the items in this budget into expenses and add them to the group",
            "Delete this budget", // TODO: Eliminate percentages method in Budget and interactor if eliminating categories?
            "Exit"
    };

    public BudgetController(boolean isInBudgetView, String GUID, BudgetRepositoryGateway budgetRepositoryGateway,
                            GroupRepositoryGateway groupRepositoryGateway,
                            ItemRepositoryGateway itemRepositoryGateway) {
        this.isInBudgetSelection = isInBudgetView;
        this.GUID = GUID;
        this.budgetRepositoryGateway = budgetRepositoryGateway; // TODO: instantiate gateways here instead of injecting? or dependency injection?
        this.groupRepositoryGateway = groupRepositoryGateway;
        this.itemRepositoryGateway = itemRepositoryGateway;
    }

    public void selectionDashboard(BudgetInOut inOut) {
        while (isInBudgetSelection) { // TODO: Variable needed? or just while (true)?
            int input = inOut.getActionView(selectionActions);

            // Output list of Budgets
            inOut.sendOutput("The budgets in this group:");
            List<String> budgets = new GroupGetBudgetNameListInteractor(budgetRepositoryGateway,
                    groupRepositoryGateway).getBudgetNameList(GUID);
//            for (String budget : budgets) {
//                inOut.sendOutput(budget);
//            }
//            inOut.sendOutput(); // Like System.out.println();
            inOut.showBudgets(budgets);

            switch (input) {
                case 1 -> {
                    // Get budget choice
                    int budgetInput = inOut.getActionView(budgets.toArray(new String[0])); // TODO: Currently, prints to choose an action; change to choose a budget; change to getChoiceView?
                    String budgetName = budgets.get(budgetInput - 1);
                    String BUID = ""; // TODO: New interactor to get BUID from name?
                    CurrentBudgetManager currentBudgetManager = new CurrentBudgetManager(BUID, budgetRepositoryGateway);
                    budgetDashboard(inOut, currentBudgetManager);
                }
                case 2 -> {
                    String name = inOut.getNewBudgetName();
                    double maxSpend = inOut.getBudgetMaxSpend();
                    new BudgetCreationInteractor(budgetRepositoryGateway, groupRepositoryGateway).create(GUID, "",
                            name, new String[0], maxSpend, 0); // TODO: eliminate categories, timeSpan? How to create BUIDs?
                    // TODO: Store the boolean returned by create method and depending on success/failure, output message?
                }
                case 3 -> {
                    return;
                }
            }
        }
    }

    public void budgetDashboard(BudgetInOut inOut, CurrentBudgetManager currentBudgetManager) {
        while (true) {
            // Return an integer between 1 and the number of actions, inclusive
            int input = inOut.getActionView(budgetActions);

            switch (input) {
                case 1 -> {
                    String name = inOut.getNewItemName();
                    double cost = inOut.getNewItemCost();
                    int quantity = inOut.getNewItemQuantity();
                    new BudgetItemAddingInteractor(budgetRepositoryGateway, itemRepositoryGateway)
                            .addItem(currentBudgetManager.getCurrentBudgetUID(), "", "", name, cost,
                                    quantity);
                    // TODO: eliminate categories? How to create IUIDs?
                    // TODO: Output message?
                }
                case 2 -> {

                }
                case 3 -> {

                }
                case 4 -> {
                    double newMaxSpend = inOut.getBudgetMaxSpend();
                    new BudgetMaxSpendInteractor(budgetRepositoryGateway)
                            .setMaxSpend(currentBudgetManager.getCurrentBudgetUID(), newMaxSpend);
                }
                case 5 -> {

                }
                case 6 -> {
                    new BudgetRemovalInteractor(budgetRepositoryGateway, groupRepositoryGateway)
                            .remove(GUID, currentBudgetManager.getCurrentBudgetUID());
                    // TODO: store boolean returned by remove method and output message?
                }
                case 7 -> {
                    return;
                }
            }
        }
    }
}
