package entities;

import entities.budget.entities.Budget;

import java.util.*;

/**
 * Below is the abstract Group class which represents the origin of our program.
 */
public class Group {
    protected String groupName;
    protected List<String> groupMembers;
    protected List<Expense> expenseList;
    protected String description;
    protected String GUID;
    protected List<Budget> budgets;

    /**
     * Construct a group with groupName, groupMembers, expenseList, and description.
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
        this.GUID = "";
        this.budgets = new ArrayList<>();
    }

    public String getGUID() {
        return this.GUID;
    }

    public String getGroupName() {
        return this.groupName;
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

    public void addBudget(Budget budget) {
        this.budgets.add(budget);
    }

    public Budget getBudget(String name) {
        for (Budget budget : budgets) {
            if (budget.getName().equals(name)) {
                return budget;
            }
        }
        return null;
    }
}
