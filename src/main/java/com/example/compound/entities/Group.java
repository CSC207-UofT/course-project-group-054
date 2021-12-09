package com.example.compound.entities;

import javax.persistence.*;
import java.util.*;

/**
 * Below is the abstract Group class which represents the origin of our program.
 */
//@Entity
//@Table(name = "groups")
public class Group {

//  Attributes

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer GUID;

    private String groupName;

    private String description;

//    @ElementCollection
    private List<Integer> members;

//    @ElementCollection
    private List<Integer> expenses;

//    @ElementCollection
    private List<Integer> budgets;
//  --------------------------------


//  Getters & Setters

//    public Integer getGuid() {
//        return this.GUID;
//    }
//    public void setGuid(Integer GUID) {
//        this.GUID = GUID;
//    }

    public String getGroupName() { return this.groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) { this.description = description; }

    public List<Integer> getMembers() { return members; }
    public List<Integer> getExpenses() { return expenses; }
    public List<Integer> getBudgets() {
        return this.budgets;
    }
//  --------------------------------

//    public void addExpense(Integer expense) {
//        this.expenses.add(expense);
//    }

//    @Override
//    public String toString() {
//        return this.groupName;
//    }

//    public void setGroupName(String name) {this.groupName = name;}

//    public void addBudget(Budget budget) {
////        this.budgets.add(budget.getBUID());
//    }

//    public Budget getBudget(String BUID) {
////        for (Budget budget : budgets) {
////            if (budget.getBUID().equals(BUID)) {
////                return budget;
////            }
////        }
//        return null;
//    }



//    public boolean removeBudget(Integer BUID) {
//        return budgets.removeIf(budget -> budget == BUID);
//    }

    public Group() {
        List<Integer> members = new ArrayList<>();
        List<Integer> expenses = new ArrayList<>();

        members.add(1);
        expenses.add(1);

        this.groupName = "ABC";
        this.description = "Description";
        this.members = members;
        this.expenses = expenses;
    }

    public Group(String name, String description, List<Integer> members, List<Integer> expenses) {
        this.groupName = name;
        this.description = description;
        this.members = members;
        this.expenses = expenses;
    }

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }


//    public void setData(String name, String description, List<Integer> members, List<Integer> expenses) {
//        this.groupName = name;
//        this.description = description;
//        this.members = members;
//        this.expenses = expenses;
//    }
}
