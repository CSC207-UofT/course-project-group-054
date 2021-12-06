package com.example.compound.repositories;

import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;

import java.util.List;

public class BudgetRepository implements RepositoryGatewayI<BudgetTransferData> {
    @Override
    public BudgetTransferData findByUID(String UID) {
        return null;
    }

    @Override
    public List<BudgetTransferData> findAll() {
        return null;
    }

    @Override
    public String save(BudgetTransferData budgetTransferData) {
        return null;
    }

    @Override
    public void deleteById(String UID) {

    }
}
