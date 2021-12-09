package com.example.compound.use_cases.transfer_data;

import com.example.compound.entities.*;

import java.util.List;

/**
 * A helper class that helps transfer the group data
 */
public class GroupTransferData {
    private final String groupName;
    private final List<String> groupMembers;
    private final List<Expense> expenseList;
    private final String description;
    private  String GUID;
    private  List<Budget> budgets;

    public GroupTransferData(String groupName, List<String> groupMembers,
                             List<Expense> expenseList, String description, String guid, List<Budget> budgets) {
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.expenseList = expenseList;
        this.description = description;
        GUID = guid;
        this.budgets = budgets;
    }

    public GroupTransferData(String groupName, List<String> groupMembers, List<Expense> expenseList, String description) {
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.expenseList = expenseList;
        this.description = description;
    }

    //Below two are methods that add budget/expense to the corresponding list.

    public void addBudget(Budget budget) {
        this.budgets.add(budget);
    }

    public void addExpense(Expense expense) {
        this.expenseList.add(expense);
    }

    //Below are just a bunch of get/set methods

    public String getGUID() {
        return GUID;
    }

    public String getName() {
        return groupName;
    }

    public List<Expense> getExpenseList() {return expenseList;}

    public List<Budget> getBudgets() {
        return this.budgets;
    }

    public void setGUID(String GUID) {this.GUID = GUID;}

    /**
     * Remove budget from the budget list
     * @param BUID the unique identifier of the budget
     * @return true iff budget has the same BUID
     */
    public boolean removeBudget(String BUID) {
        return budgets.removeIf(budget -> budget.getBUID().equals(BUID));
    }

}
