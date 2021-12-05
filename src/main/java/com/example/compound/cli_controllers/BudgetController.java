package com.example.compound.cli_controllers;

import com.example.compound.entities.Group;
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

public class BudgetController {
    private final String GUID;
    private final CurrentBudgetManager currentBudgetManager;
    private final BudgetManager budgetManager;
    private final ExpenseManager expenseManager;
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
            "Delete this budget",
            "Exit"
    };

    public BudgetController(String GUID,
                            RepositoryGatewayI<BudgetTransferData> budgetRepository,
                            RepositoryGatewayI<GroupTransferData> groupRepository,
                            RepositoryGatewayI<ItemTransferData> itemRepository,
//                            RepositoryGateway repositoryGateway,
                            ExpenseManager expenseManager) {
        this.GUID = GUID;
        this.currentBudgetManager = new CurrentBudgetManager(budgetRepository);
//        this.currentBudgetManager = new CurrentBudgetManager(repositoryGateway);
        this.budgetManager = new BudgetManager(budgetRepository, groupRepository, itemRepository);
//        this.budgetManager = new BudgetManager(repositoryGateway);
        this.expenseManager = expenseManager;
    }

    /**
     * Request that the user take an action relating to managing a list of budgets associated with a Group and then take
     * the appropriate action.
     * @param inOut the user interface object
     */
    public void groupBudgetsDashboard(InOut inOut) {
        while (true) {
            int input = inOut.getOptionView(selectionActions);

            switch (input) {
                case 1 -> {
                    // Output list of Budgets
                    List<String> budgets;
                    try {
                        budgets = budgetManager.getBudgetNameList(GUID);
                    } catch (NullPointerException e) {
                        inOut.sendOutput("The requested group or budget does not exist. Please try again.");
                        break;
                    }
                    // Get budget choice
                    if (budgets.size() == 0) {
                        inOut.sendOutput("This group does not have any budgets yet.");
                    } else {
                        inOut.sendOutput("The budgets in this group:");
                        int budgetInput = inOut.getOptionView(budgets.toArray(new String[0]));
                        String budgetName = budgets.get(budgetInput - 1);
                        String BUID;
                        try {
                            BUID = Objects.requireNonNull(budgetManager.getBUIDFromName(budgetName));
                        } catch (NullPointerException e) {
                            inOut.sendOutput("The requested budget does not exist. Please try again."); // TODO: Should this ever happen, and if not, do we need requireNonNull?
                            break;
                        }
                        currentBudgetManager.setCurrentBudget(BUID);
                        budgetDashboard(inOut);
                    }
                }
                case 2 -> {
                    String name = inOut.requestInput("the name of the budget");
                    double maxSpend = requestDouble(inOut, "the maximum amount of money that can be spent on " +
                            "items in this budget.\nDo not include a dollar sign. For example: 12.34");

                    if (budgetManager.create(GUID, name, maxSpend)) {
                        inOut.sendOutput("A new budget was successfully added to the group.");
                    } else {
                        inOut.sendOutput("The requested group does not exist. Please try again.");
                    }
                }
                case 3 -> {
                    return;
                }
            }
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
            inOut.sendOutput("Please enter a valid amount!");
            return requestDouble(inOut, attribute);
        }
    }

    /**
     * Request that the user enter an integer for the given attribute via the user interface and return the input
     * integer.
     * @param inOut the user interface object
     * @param request the attribute for which the user is requested to input an integer
     * @return the integer input by the user
     */
    public int requestInt(InOut inOut, String request) {
        String maxSpendInput = inOut.requestInput(request);
        try {
            return Integer.parseInt(maxSpendInput);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount!");
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
            int input = inOut.getOptionView(budgetActions);

            switch (input) {
                case 1 -> {
                    String name = inOut.requestInput("the item's name");
                    double cost = requestDouble(inOut, "the item's cost");
                    int quantity = requestInt(inOut, "the item's quantity");
                    try {
                        Objects.requireNonNull(budgetManager.addItem(currentBudgetManager.getCurrentBudgetUID(), name,
                                cost, quantity));
                    } catch (NullPointerException e) {
                        inOut.sendOutput("The item could not be created because the associated budget does not " +
                                "exist.");
                        break;
                    }
                    inOut.sendOutput("The item was successfully created.");
                }
                case 2 -> {
                    String IUID;
                    try {
                        IUID = Objects.requireNonNull(getIUID(inOut));
                    } catch (NullPointerException e) {
                        break;
                    }
                    int newQuantity = requestInt(inOut, "the new quantity");
                    if (!budgetManager.changeItemQuantity(IUID, newQuantity)) {
                        inOut.sendOutput("The item's quantity could not be changed because the requested budget " +
                                "does not contain the requested item. Please try again.");
                    }
                }
                case 3 -> {
                    String IUID;
                    try {
                        IUID = Objects.requireNonNull(getIUID(inOut));
                    } catch (NullPointerException e) {
                        break;
                    }
                    if (!budgetManager.removeItem(IUID)) {
                        inOut.sendOutput("The item could not be removed because the requested budget does not " +
                                "contain the requested item. Please try again.");
                    }
                }
                case 4 -> {
                    double newMaxSpend = requestDouble(inOut, "the new spending limit");
                    if (!budgetManager.setMaxSpend(currentBudgetManager.getCurrentBudgetUID(), newMaxSpend)) {
                        inOut.sendOutput("The requested budget does not exist. Please try again.");
                    }
                }
                case 5 -> {
                    if (budgetManager.addExpensesToGroup(GUID, currentBudgetManager.getCurrentBudgetUID(),
                            expenseManager)) {
                        inOut.sendOutput("The expenses in this budget were added to the current group.");
                    } else {
                        inOut.sendOutput("The expenses in this budget could not be added to the current group " +
                                "because the requested group or budget does not exist.");
                    }
                }
                case 6 -> {
                    if (budgetManager.remove(GUID, currentBudgetManager.getCurrentBudgetUID())) {
                        inOut.sendOutput("The budget was removed successfully.");
                    } else {
                        inOut.sendOutput("The budget could not be removed because the requested group or budget " +
                                "does not exist. Please try again.");
                    }
                }
                case 7 -> {
                    return;
                }
            }
        }
    }

    /**
     * Output a list of items in the current budget and return the IUID of the item chosen by the user.
     * @param inOut the user interface object
     * @return the IUID of the item chosen by the user, or null if there are no items in the budget or the chosen item
     *         does not exist
     */
    public String getIUID(InOut inOut) {
        List<String> items = budgetManager.getItems(currentBudgetManager.getCurrentBudgetUID());
        if (items == null) {
            inOut.sendOutput("The requested budget does not exist. Please try again."); // TODO: Should this ever happen, and if not, do we need requireNonNull?
            return null;
        } else if (items.size() == 0) {
            inOut.sendOutput("There are currently no items in this budget.");
            return null;
        }
        int itemInput = inOut.getOptionView(items.toArray(new String[0]));
        String itemName = items.get(itemInput - 1);
        String IUID;
        try {
            IUID = Objects.requireNonNull(budgetManager.getIUIDFromName(itemName));
        } catch (NullPointerException e) {
            inOut.sendOutput("The requested item does not exist. Please try again."); // TODO: Should this ever happen, and if not, do we need requireNonNull?
            return null;
        }
        return IUID;
    }
}
