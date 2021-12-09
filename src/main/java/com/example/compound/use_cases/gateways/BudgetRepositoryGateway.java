package com.example.compound.use_cases.gateways;

import com.example.compound.entities.Budget;

import java.util.List;

public interface BudgetRepositoryGateway extends RepositoryGatewayI<Budget> {
    Budget findById(String BUID);

    List<Budget> findAll();

    // Returns a new BUID
    String save(Budget budget);

    boolean update(Budget budget);

    boolean deleteById(String BUID);
}
