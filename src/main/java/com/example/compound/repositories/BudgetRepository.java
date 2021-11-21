package com.example.compound.repositories;

import com.example.compound.entities.Budget;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;

import java.util.List;

public class BudgetRepository implements RepositoryGatewayI<Budget> {
    @Override
    public Budget findByUID(String UID) {
        return null;
    }

    @Override
    public List<Budget> findAll() {
        return null;
    }

    @Override
    public String save(Budget budget) {
        return null;
    }

    @Override
    public void deleteById(String UID) {

    }
}
