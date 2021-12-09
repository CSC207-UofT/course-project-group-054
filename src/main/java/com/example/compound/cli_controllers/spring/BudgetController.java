package com.example.compound.cli_controllers.spring;

import com.example.compound.use_cases.BudgetManager;
import com.example.compound.use_cases.CurrentBudgetManager;
import com.example.compound.use_cases.CurrentGroupManager;
import com.example.compound.use_cases.ExpenseManager;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;
import com.example.compound.use_cases.transfer_data.GroupTransferData;
import com.example.compound.use_cases.transfer_data.ItemTransferData;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * A Controller managing input and output for Budget use cases.
 */
@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    private final BudgetManager budgetManager;
    private final CurrentGroupManager currentGroupManager;
    private final CurrentBudgetManager currentBudgetManager;
    private final ExpenseManager expenseManager;

    /**
     * Construct a new BudgetController with the given parameters.
     * @param currentGroupManager a use case containing the current group
     * @param budgetRepository    the repository for budgets
     * @param itemRepository      the repository for items
     * @param groupRepository     the repository for groups
     */
    public BudgetController(CurrentGroupManager currentGroupManager,
                            RepositoryGatewayI<BudgetTransferData> budgetRepository,
                            RepositoryGatewayI<ItemTransferData> itemRepository,
                            RepositoryGatewayI<GroupTransferData> groupRepository) {
        this.currentBudgetManager = new CurrentBudgetManager(budgetRepository);
        this.budgetManager = new BudgetManager(budgetRepository, groupRepository, itemRepository);
        this.expenseManager = new ExpenseManager(null); // TODO
        this.currentGroupManager = currentGroupManager;
//        this.expenseManager = new ExpenseManager(expenseRepository);
    }

    /**
     * Create a new budget with the given name and spending limit.
     * @param name     the budget's name
     * @param maxSpend the budget's spending limit
     * @return whether the budget was created
     */
    @PutMapping("/create")
    public boolean createBudget(@RequestParam String name, @RequestParam double maxSpend) {
        return budgetManager.create(currentGroupManager.getCurrentGroupUID(), name, maxSpend);
    }

    /**
     * Return a list of the names of the budgets in the current budget repository.
     * @return a list of the names of the budgets in the current budget repository
     */
    @GetMapping("/select")
    public List<String> getBudgets() {
        return budgetManager.getBudgetNameList(currentGroupManager.getCurrentGroupUID());
    }

    /**
     * Given a valid index for the list returned by getBudgets, set the current budget to the budget with the name
     * corresponding to the index.
     * @param position a valid index for the list returned by getBudgets
     */
    @PostMapping("/select")
    public void setBudget(@RequestParam int position) {
        List<String> names = budgetManager.getBudgetNameList(currentGroupManager.getCurrentGroupUID());
        String BUID = budgetManager.getBUIDFromName(names.get(position));
        currentBudgetManager.setCurrentBudget(BUID);
    }

    /**
     * Return a list of the names of the items in the current item repository.
     * @return a list of the names of the items in the current item repository
     */
    @GetMapping("/select")
    public List<String> getItems() {
        return budgetManager.getBudgetNameList(currentGroupManager.getCurrentGroupUID());
    }

    /**
     * Add an item with the given name, cost, and quantity to the current budget.
     * @param name     the item's name
     * @param cost     the item's cost
     * @param quantity the item's quantity
     * @return whether the item was added
     */
    @PutMapping("/item/create")
    public boolean addItem(@RequestParam String name, @RequestParam double cost, @RequestParam int quantity) {
        try {
            Objects.requireNonNull(budgetManager.addItem(currentBudgetManager.getCurrentBudgetUID(), name,
                    cost, quantity));
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Given a valid index for the list returned by getItems, change the quantity of the item with the name
     * corresponding to the index to the given value
     * @param position    a valid index for the list returned by getItems
     * @param newQuantity the item's new quantity
     * @return whether the quantity was changed
     */
    @PostMapping("/item/update")
    public boolean changeItemQuantity(@RequestParam int position, @RequestParam int newQuantity) {
        String IUID = getIUIDFromPosition(position);
        return budgetManager.changeItemQuantity(IUID, newQuantity);
    }

    /**
     * Given a valid index for the list returned by getItems, remove the item with the name corresponding to the index.
     * @param position a valid index for the list returned by getItems
     * @return whether the item was removed
     */
    @DeleteMapping("/item/update")
    public boolean removeItem(@RequestParam int position) {
        String IUID = getIUIDFromPosition(position);
        return budgetManager.removeItem(IUID);
    }

    /**
     * Change the spending limit of the current budget to the given value.
     * @param newMaxSpend the new spending limit
     * @return whether the spending limit was changed
     */
    @PostMapping("/update")
    public boolean changeSpendingLimit(@RequestParam double newMaxSpend) {
        return budgetManager.setMaxSpend(currentBudgetManager.getCurrentBudgetUID(), newMaxSpend);
    }

    /**
     * Convert the items in the current budget into expenses and add them to the current group.
     * @return whether the expenses were added
     */
    @PostMapping("/update")
    public boolean convertToExpenses() {
        return budgetManager.addExpensesToGroup(currentGroupManager.getCurrentGroupUID(),
                currentBudgetManager.getCurrentBudgetUID(), expenseManager);
    }

    /**
     * Delete the current budget.
     * @return whether the budget was deleted
     */
    @DeleteMapping("/update")
    public boolean deleteBudget() {
        return budgetManager.remove(currentGroupManager.getCurrentGroupUID(), currentBudgetManager.getCurrentBudgetUID());
    }

    /**
     * Given a valid index for the list returned by getItems, return the name corresponding to the index.
     * @param position a valid index for the list returned by getItems
     * @return the name corresponding to the given index
     */
    private String getIUIDFromPosition(int position) {
        List<String> names = budgetManager.getItems(currentBudgetManager.getCurrentBudgetUID());
        return budgetManager.getIUIDFromName(names.get(position));
    }
}
