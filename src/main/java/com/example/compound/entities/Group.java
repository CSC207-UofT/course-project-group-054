package com.example.compound.entities;

import com.example.compound.entities.Budget;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;

/**
 * Below is the abstract Group class which represents the origin of our program.
 */
//@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String GUID;

    protected String groupName;

    @Type(type = "list-array")
    protected List<Integer> groupMembers;

    @Type(type = "list-array")
    protected List<Expense> expenseList;

    protected String description;

//    @Type(type = "list-array")
    protected List<Budget> budgets;

    /**
     * Construct a group with groupName, groupMembers, expenseList, and description.
     * @param groupName the name of the group
     * @param groupMembers the members in the group
     * @param expenseList the list of expenses in the group
     * @param description the description of the group
     */
//    public Group(String groupName, List<String> groupMembers, List<Expense> expenseList, String description) {
//        this.groupName = groupName;
//        this.groupMembers = groupMembers;
//        this.expenseList = expenseList;
//        this.description = description;
//        this.GUID = "";
//        this.budgets = new ArrayList<>();
//    }

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

    public Budget getBudget(String BUID) {
        for (Budget budget : budgets) {
            if (budget.getBUID().equals(BUID)) {
                return budget;
            }
        }
        return null;
    }

    public List<Budget> getBudgets() {
        return this.budgets;
    }

    public boolean removeBudget(String BUID) {
        return budgets.removeIf(budget -> budget.getBUID().equals(BUID));
    }

    public Group() {}


    public void setData(String name, String description, List<Integer> members, List<Integer> expenses) {
        this.groupName = name;
        this.description = description;
        this.groupMembers = members;
    }
}
