package com.example.compound.controller;

import com.example.compound.use_cases.BudgetManager;
import com.example.compound.use_cases.ExpenseManager;
import com.example.compound.use_cases.RepositoryGateway;
import com.example.compound.use_cases.budget.CurrentBudgetManager;
import com.example.compound.use_cases.budget.gateways.BudgetRepositoryGateway;
import com.example.compound.use_cases.budget.gateways.ItemRepositoryGateway;
import com.example.compound.use_cases.budget.interactors.*;
import com.example.compound.use_cases.group.GroupAddingExpensesFromBudgetInteractor;
import com.example.compound.use_cases.group.GroupGetBudgetNameListInteractor;
import com.example.compound.use_cases.group.GroupRepositoryGateway;

import java.util.List;

public class BudgetController {
    private final String GUID;
    private final BudgetRepositoryGateway budgetRepositoryGateway;
    private final GroupRepositoryGateway groupRepositoryGateway;
    private final ItemRepositoryGateway itemRepositoryGateway;
    private final RepositoryGateway repositoryGateway;
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
            "Delete this budget", // TODO: Eliminate percentages method in Budget and interactor if eliminating categories?
            "Exit"
    };

    public BudgetController(String GUID,
                            BudgetRepositoryGateway budgetRepositoryGateway,
                            GroupRepositoryGateway groupRepositoryGateway,
                            ItemRepositoryGateway itemRepositoryGateway,
                            RepositoryGateway repositoryGateway,
                            ExpenseManager expenseManager) {
        this.GUID = GUID;
        this.budgetRepositoryGateway = budgetRepositoryGateway; // TODO: instantiate gateways here instead of injecting? or dependency injection?
        this.groupRepositoryGateway = groupRepositoryGateway;
        this.itemRepositoryGateway = itemRepositoryGateway;
        this.repositoryGateway = repositoryGateway;
        this.currentBudgetManager = new CurrentBudgetManager(budgetRepositoryGateway);
        this.budgetManager = new BudgetManager(budgetRepositoryGateway, groupRepositoryGateway, itemRepositoryGateway, repositoryGateway);
        this.expenseManager = expenseManager;
    }

    public void selectionDashboard(InOut inOut) {
        boolean isInBudgetSelection = true;
        while (isInBudgetSelection) { // TODO: Variable needed? or just while (true)?
            int input = inOut.getActionView(selectionActions);

            switch (input) {
                case 1 -> {
                    // Output list of Budgets
                    inOut.sendOutput("The budgets in this group:");
                    List<String> budgets = new GroupGetBudgetNameListInteractor(budgetRepositoryGateway,
                            groupRepositoryGateway).getBudgetNameList(GUID);

                    // Get budget choice
                    if (budgets.size() == 0) {
                        inOut.sendOutput("This group does not have any budgets yet.");
                    } else {
                        int budgetInput = inOut.getActionView(budgets.toArray(new String[0])); // TODO: Currently, prints to choose an action; change to choose a budget; change to getChoiceView?
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
                case 3 -> isInBudgetSelection = false;
            }
        }
    }

    public double requestDouble(InOut inOut, String request) {
        String maxSpendInput = inOut.requestInput(request);
        try {
            return Double.parseDouble(maxSpendInput);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount of money.");
            return requestDouble(inOut, request);
        }
    }

    public int requestInt(InOut inOut, String request) {
        String maxSpendInput = inOut.requestInput(request);
        try {
            return Integer.parseInt(maxSpendInput);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount of money.");
            return requestInt(inOut, request);
        }
    }

    public void budgetDashboard(InOut inOut, CurrentBudgetManager currentBudgetManager) {
        while (true) {
            // Return an integer between 1 and the number of actions, inclusive
            int input = inOut.getActionView(budgetActions);

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
                    new BudgetMaxSpendInteractor(budgetRepositoryGateway)
                            .setMaxSpend(currentBudgetManager.getCurrentBudgetUID(), newMaxSpend);
                }
                case 5 -> new GroupAddingExpensesFromBudgetInteractor(budgetRepositoryGateway, groupRepositoryGateway)
                        .addExpensesFromBudget(GUID, currentBudgetManager.getCurrentBudgetUID(), budgetManager, expenseManager);
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

    public String getIUID(InOut inOut, CurrentBudgetManager currentBudgetManager) {
        List<String> items = budgetManager.getItems(currentBudgetManager.getCurrentBudgetUID());
        int itemInput = inOut.getActionView(items.toArray(new String[0])); // TODO: Currently, prints to choose an action; change to choose a budget; change to getChoiceView?
        String itemName = items.get(itemInput - 1);
        return budgetManager.getIUIDFromName(itemName);
    }
}
