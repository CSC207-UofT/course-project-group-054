package com.example.compound.api.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @ElementCollection
    private List<Integer> members;

    @ElementCollection
    private List<Integer> expenses;

    @ElementCollection
    private List<Integer> budgets;

    public Integer getId() {
        return id;
    }

    public List<Integer> getMembers() {
        return members;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getBudgets() {
        return budgets;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getExpenses() {
        return expenses;
    }

    public Group() {}

    public Group(String name,
                 String description,
                 List<Integer> members,
                 List<Integer> expenses,
                 List<Integer> budgets) {
        this.name = name;
        this.description = description;
        this.members = members;
        this.expenses = expenses;
        this.budgets = budgets;
    }
}
