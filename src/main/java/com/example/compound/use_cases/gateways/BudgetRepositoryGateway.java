package com.example.compound.use_cases.gateways;

import com.example.compound.entities.Budget;

import java.util.List;

public interface BudgetRepositoryGateway {
    Budget findById(String BUID);

    List<Budget> findAll();

    // Returns a new BUID
    String save(Budget budget); // TODO: Separate methods for saving a new object and updating an existing one?

    void deleteById(String BUID);
}
