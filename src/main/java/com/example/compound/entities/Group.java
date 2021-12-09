package com.example.compound.entities;

import java.util.*;

/**
 * Below is the abstract Group class which represents the origin of our program.
 */
public class Group {
    private String groupName;
    private final List<String> groupMembers;
    private final List<Expense> expenseList;
    private final String description;
    private String GUID;
    private final List<Budget> budgets;

    /**
     * Construct a group with the given name, list of members, list of expenses, and description.
     * @param groupName the name of the group
     * @param groupMembers the members in the group
     * @param expenseList the list of expenses in the group
     * @param description the description of the group
     */
    public Group(String groupName, List<String> groupMembers, List<Expense> expenseList, String description) {
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.expenseList = expenseList;
        this.description = description;
        this.GUID = ""; // Implemented in the repository
        this.budgets = new ArrayList<>();
    }

    public String getGUID() {
        return this.GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void addExpense(Expense expense) {
        this.expenseList.add(expense);
    }

    /**
     * Return a list of strings containing emails of group members
     *
     * @return A list of strings containing emails of group members
     */
    public List<String> getGroupMembers() {
        return this.groupMembers;
    }

    public List<Expense> getExpenseList() {
        return this.expenseList;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.groupName;
    }

    public void setGroupName(String name) {this.groupName = name;}

    public void addBudget(Budget budget) {
        this.budgets.add(budget);
    }

    public List<Budget> getBudgets() {
        return this.budgets;
    }

    public boolean removeBudget(String BUID) {
        return budgets.removeIf(budget -> budget.getBUID().equals(BUID));
    }

    public void removeMember(String email){
        this.groupMembers.remove(email);
    }

    public void addMember(String email){
        this.groupMembers.add(email);
    }
}
