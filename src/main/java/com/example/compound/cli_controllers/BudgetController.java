package com.example.compound.cli_controllers;

import com.example.compound.presenters.BudgetPresenter;
import com.example.compound.use_cases.BudgetManager;
import com.example.compound.use_cases.ExpenseManager;
import com.example.compound.use_cases.CurrentBudgetManager;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;
import com.example.compound.use_cases.transfer_data.GroupTransferData;
import com.example.compound.use_cases.transfer_data.ItemTransferData;
//import com.example.compound.use_cases.gateways.RepositoryGateway;

import java.util.List;
import java.util.Objects;

/**
 * A Controller managing input and output for Budget use cases.
 */
public class BudgetController {
    private final String GUID;
    private final CurrentBudgetManager currentBudgetManager;
    private final BudgetManager budgetManager;
    private final ExpenseManager expenseManager;
    private final BudgetPresenter budgetPresenter;

    /**
     * Construct a new BudgetController with the given parameters.
     * @param GUID             the UID of the current group
     * @param budgetRepository the repository for budgets
     * @param groupRepository  the repository for groups
     * @param itemRepository   the repository for items
     * @param expenseManager   the manager for expenses
     */
    public BudgetController(String GUID,
                            RepositoryGatewayI<BudgetTransferData> budgetRepository,
                            RepositoryGatewayI<GroupTransferData> groupRepository,
                            RepositoryGatewayI<ItemTransferData> itemRepository,
//                            RepositoryGateway repositoryGateway,
                            ExpenseManager expenseManager) {
        this.GUID = GUID;
        this.currentBudgetManager = new CurrentBudgetManager(budgetRepository);
        this.budgetManager = new BudgetManager(budgetRepository, groupRepository, itemRepository);
        this.expenseManager = expenseManager;
        this.budgetPresenter = new BudgetPresenter();
    }

    /**
     * Request that the user take an action relating to managing a list of budgets associated with a Group and then take
     * the appropriate action.
     * @param inOut the user interface object
     */
    public void groupBudgetsDashboard(InOut inOut) {
        while (true) {
            int input = inOut.getOptionView(budgetPresenter.requestSelectionActions());

            switch (input) {
                case 1 -> selectBudget(inOut);
                case 2 -> createNewBudget(inOut);
                case 3 -> {
                    return;
                }
            }
        }
    }

    /**
     * Have the user select a budget.
     * @param inOut the user interface object
     */
    private void selectBudget(InOut inOut) {
        // Output list of Budgets
        List<String> budgets;
        try {
            budgets = budgetManager.getBudgetNameList(GUID);
        } catch (NullPointerException e) {
            inOut.sendOutput(budgetPresenter.getBudgetExistence(false));
            return;
        }
        // Get budget choice
        if (budgets.size() == 0) {
            inOut.sendOutput(budgetPresenter.getBudgetExistence(false));
        } else {
            // inOut.sendOutput("The budgets in this group:");
            int budgetInput = inOut.getOptionView(budgets.toArray(new String[0]));
            String budgetName = budgets.get(budgetInput - 1);
            String BUID;
            try {
                BUID = Objects.requireNonNull(budgetManager.getBUIDFromName(budgetName));
            } catch (NullPointerException e) {
                inOut.sendOutput(budgetPresenter.getAuthenticationCheck(false));
                return;
            }
            currentBudgetManager.setCurrentBudget(BUID);
            budgetDashboard(inOut);
        }
    }

    /**
     * Have the user create a new budget.
     * @param inOut the user interface object
     */
    private void createNewBudget(InOut inOut) {
        String name = inOut.requestInput(budgetPresenter.requestName());
        double maxSpend = requestDouble(inOut, budgetPresenter.requestAmount());

        if (budgetManager.create(GUID, name, maxSpend)) {
            inOut.sendOutput(budgetPresenter.getFailure(false));
        } else {
            inOut.sendOutput(budgetPresenter.getFailure(true));
        }
    }

    /**
     * A helper method that requests the user to enter input for the given attribute and converts the input string
     * returned by the given user interface object to a double.
     * @param inOut     the user interface object
     * @param attribute the attribute for which the user interface object requests the user to enter input
     * @return the double input by the user
     */
    private double requestDouble(InOut inOut, String attribute) {
        String input = inOut.requestInput(attribute);
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            inOut.sendOutput(budgetPresenter.getValidAmount(false));
            return requestDouble(inOut, budgetPresenter.requestAmount());
        }
    }

    /**
     * Request that the user enter an integer for the given attribute via the user interface and return the input
     * integer.
     * @param inOut the user interface object
     * @param request the attribute for which the user is requested to input an integer
     * @return the integer input by the user
     */
    private int requestInt(InOut inOut, String request) {
        String maxSpendInput = inOut.requestInput(request);
        try {
            return Integer.parseInt(maxSpendInput);
        } catch (NumberFormatException e) {
            budgetPresenter.getValidAmount(false);
            return requestInt(inOut, request);
        }
    }

    /**
     * Request that the user take an action relating to managing a single budget and then take the appropriate action.
     * @param inOut the user interface object
     */
    public void budgetDashboard(InOut inOut) {
        while (true) {
            // Return an integer between 1 and the number of actions, inclusive
            int input = inOut.getOptionView(budgetPresenter.requestBudgetActions());

            switch (input) {
                case 1 -> addItem(inOut);
                case 2 -> changeItemQuantity(inOut);
                case 3 -> removeItem(inOut);
                case 4 -> changeSpendingLimit(inOut);
                case 5 -> convertToExpenses(inOut);
                case 6 -> {
                    deleteBudget(inOut);
                    return;
                }
                case 7 -> {
                    return;
                }
            }
        }
    }

    /**
     * Have the user add an item.
     * @param inOut the user interface object
     */
    private void addItem(InOut inOut) {
        String name = inOut.requestInput(budgetPresenter.requestName());
        double cost = requestDouble(inOut, budgetPresenter.requestCost());
        int quantity = requestInt(inOut, budgetPresenter.requestQuantity());
        try {
            Objects.requireNonNull(budgetManager.addItem(currentBudgetManager.getCurrentBudgetUID(), name,
                    cost, quantity));
        } catch (NullPointerException e) {
            inOut.sendOutput(budgetPresenter.getFailure(true));
            return;
        }
        inOut.sendOutput(budgetPresenter.getFailure(false));
    }

    /**
     * Have the user change an item's quantity.
     * @param inOut the user interface object
     */
    private void changeItemQuantity(InOut inOut) {
        String IUID;
        try {
            IUID = Objects.requireNonNull(getIUID(inOut));
        } catch (NullPointerException e) {
            return;
        }
        int newQuantity = requestInt(inOut, budgetPresenter.requestInt());
        if (!budgetManager.changeItemQuantity(IUID, newQuantity)) {
            inOut.sendOutput(budgetPresenter.getFailure(true));
        }
    }

    /**
     * Have the user remove an item.
     * @param inOut the user interface object
     */
    private void removeItem(InOut inOut) {
        String IUID;
        try {
            IUID = Objects.requireNonNull(getIUID(inOut));
        } catch (NullPointerException e) {
            return;
        }
        if (!budgetManager.removeItem(IUID)) {
            inOut.sendOutput(budgetPresenter.getFailure(true));
        }
    }

    /**
     * Have the user change the spending limit of a budget.
     * @param inOut the user interface object
     */
    private void changeSpendingLimit(InOut inOut) {
        double newMaxSpend = requestDouble(inOut, budgetPresenter.requestAmount());
        if (!budgetManager.setMaxSpend(currentBudgetManager.getCurrentBudgetUID(), newMaxSpend)) {
            inOut.sendOutput(budgetPresenter.getFailure(true));
        }
    }

    /**
     * Convert the items in the current budget into expenses and add them to the current group.
     * @param inOut the user interface object
     */
    private void convertToExpenses(InOut inOut) {
        if (budgetManager.addExpensesToGroup(GUID, currentBudgetManager.getCurrentBudgetUID(),
                expenseManager)) {
            inOut.sendOutput(budgetPresenter.getFailure(false));
        } else {
            inOut.sendOutput(budgetPresenter.getFailure(true));
        }
    }

    /**
     * Delete the current budget.
     * @param inOut the user interface object
     */
    private void deleteBudget(InOut inOut) {
        if (budgetManager.remove(GUID, currentBudgetManager.getCurrentBudgetUID())) {
            inOut.sendOutput(budgetPresenter.getFailure(false));
        } else {
            inOut.sendOutput(budgetPresenter.getFailure(true));
        }
    }

    /**
     * Output a list of items in the current budget and return the IUID of the item chosen by the user.
     * @param inOut the user interface object
     * @return the IUID of the item chosen by the user, or null if there are no items in the budget or the chosen item
     *         does not exist
     */
    private String getIUID(InOut inOut) {
        List<String> items = budgetManager.getItems(currentBudgetManager.getCurrentBudgetUID());
        if (items == null) {
            inOut.sendOutput(budgetPresenter.getBudgetExistence(false));
            return null;
        } else if (items.size() == 0) {
            inOut.sendOutput(budgetPresenter.noItemAvailable(true));
            return null;
        }
        int itemInput = inOut.getOptionView(items.toArray(new String[0]));
        String itemName = items.get(itemInput - 1);
        String IUID;
        try {
            IUID = Objects.requireNonNull(budgetManager.getIUIDFromName(itemName));
        } catch (NullPointerException e) {
            inOut.sendOutput(budgetPresenter.noItemAvailable(true));
            return null;
        }
        return IUID;
    }
}
