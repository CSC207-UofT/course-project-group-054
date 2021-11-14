package com.example.compound.use_cases.budget.gateways;

import com.example.compound.entities.Budget;

import java.util.List;

public interface BudgetRepositoryGateway {
    Budget findById(String BUID);

    List<Budget> findAll();

    Budget save(Budget budget); // TODO: Separate methods for saving a new object and updating an existing one?

    void deleteById(String BUID);

//    // ...
//
//    Budget loadBudgetFromBUID(String BUID);
//
//    Budget loadBudgetFromIUID(String IUID, ItemRepositoryGateway itemRepositoryGateway);
//
//    boolean createBudget(Budget budget); // TODO: Create a new data transfer object (see https://piazza.com/class/kt4hlydpsym1bz?cid=843)
//
//    boolean setMaxSpend(Budget budget, double newMaxSpend);
//
//    double getMaxSpend(Budget budget);
//
//    boolean addItem(Budget budget, Item newItem);
//
//    boolean removeItem(Budget budget, String IUID);
//
//    HashMap<String, Double> getPercentages(Budget budget);
//
//    List<Expense> getExpenses(Budget budget);
}
