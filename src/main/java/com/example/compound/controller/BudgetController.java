package com.example.compound.controller;

import com.example.compound.use_cases.BudgetManager;
import com.example.compound.use_cases.ExpenseManager;
//import com.example.compound.use_cases.gateways.RepositoryGateway;
import com.example.compound.use_cases.CurrentBudgetManager;
//import com.example.compound.use_cases.gateways.BudgetRepositoryGateway;
//import com.example.compound.use_cases.gateways.ItemRepositoryGateway;
//import com.example.compound.use_cases.gateways.GroupRepositoryGateway;
import com.example.compound.use_cases.gateways.RepositoryGateway;

import java.util.List;

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
//                            BudgetRepositoryGateway budgetRepositoryGateway,
//                            GroupRepositoryGateway groupRepositoryGateway,
//                            ItemRepositoryGateway itemRepositoryGateway,
                            RepositoryGateway repositoryGateway,
                            ExpenseManager expenseManager) {
        this.GUID = GUID;
//        this.currentBudgetManager = new CurrentBudgetManager(budgetRepositoryGateway);
        this.currentBudgetManager = new CurrentBudgetManager(repositoryGateway);
//        this.budgetManager = new BudgetManager(budgetRepositoryGateway, groupRepositoryGateway, itemRepositoryGateway);
        this.budgetManager = new BudgetManager(repositoryGateway);
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
                    inOut.sendOutput("The budgets in this group:");
                    List<String> budgets = budgetManager.getBudgetNameList(GUID);

                    // Get budget choice
                    if (budgets.size() == 0) {
                        inOut.sendOutput("This group does not have any budgets yet.");
                    } else {
                        int budgetInput = inOut.getOptionView(budgets.toArray(new String[0]));
                        String budgetName = budgets.get(budgetInput - 1);
                        String BUID = budgetManager.getBUIDFromName(budgetName);
                        currentBudgetManager.setCurrentBudget(BUID);
                        budgetDashboard(inOut, currentBudgetManager);
                    }
                }
                case 2 -> {
                    String name = inOut.requestInput("the name of the budget");
                    double maxSpend = requestDouble(inOut, "the maximum amount of money that can be spent on " +
                            "items in this budget.\nDo not include a dollar sign. For example: 12.34");

                    if (budgetManager.create(GUID, name, maxSpend)) {
                        inOut.sendOutput("A new budget was successfully added to the given group.");
                    } else {
                        inOut.sendOutput("The budget could not be added to the given group. Please try again.");
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
            System.out.println("Please enter a valid amount of money.");
            return requestInt(inOut, request);
        }
    }

    /**
     * Request that the user take an action relating to managing a single budget and then take the appropriate action.
     * @param inOut the user interface object
     * @param currentBudgetManager the CurrentBudgetManager instance storing the current budget
     */
    public void budgetDashboard(InOut inOut, CurrentBudgetManager currentBudgetManager) {
        while (true) {
            // Return an integer between 1 and the number of actions, inclusive
            int input = inOut.getOptionView(budgetActions);

            switch (input) {
                case 1 -> {
                    String name = inOut.requestInput("the item's name");
                    double cost = requestDouble(inOut, "the item's cost");
                    int quantity = requestInt(inOut, "the item's quantity");
                    budgetManager.addItem(currentBudgetManager.getCurrentBudgetUID(), name, cost, quantity);
                    inOut.sendOutput("The item was successfully created.");
                }
                case 2 -> {
                    String IUID = getIUID(inOut, currentBudgetManager);
                    int newQuantity = requestInt(inOut, "the new quantity");
                    budgetManager.changeItemQuantity(IUID, newQuantity);
                }
                case 3 -> {
                    String IUID = getIUID(inOut, currentBudgetManager);
                    budgetManager.removeItem(IUID);
                }
                case 4 -> {
                    double newMaxSpend = requestDouble(inOut, "the new spending limit");
                    budgetManager.setMaxSpend(currentBudgetManager.getCurrentBudgetUID(), newMaxSpend);
                }
                case 5 -> budgetManager.addExpensesToGroup(GUID, currentBudgetManager.getCurrentBudgetUID(),
                        expenseManager);
                case 6 -> {
                    if (budgetManager.remove(GUID, currentBudgetManager.getCurrentBudgetUID())) {
                        inOut.sendOutput("The item was removed.");
                    } else {
                        inOut.sendOutput("The item could not be removed. Please try again.");
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
     * @param currentBudgetManager the CurrentBudgetManager instance storing the current budget
     * @return the IUID of the item chosen by the user
     */
    public String getIUID(InOut inOut, CurrentBudgetManager currentBudgetManager) {
        List<String> items = budgetManager.getItems(currentBudgetManager.getCurrentBudgetUID());
        int itemInput = inOut.getOptionView(items.toArray(new String[0]));
        String itemName = items.get(itemInput - 1);
        return budgetManager.getIUIDFromName(itemName);
    }
}
