package com.example.compound.repositories;

import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.ItemTransferData;

import java.util.List;

public class ItemRepository implements RepositoryGatewayI<ItemTransferData> {
    @Override
    public ItemTransferData findByUID(String UID) {
        return null;
    }

    @Override
    public List<ItemTransferData> findAll() {
        return null;
    }

    @Override
    public String save(ItemTransferData itemTransferData) {
        return null;
    }

    @Override
    public void deleteById(String UID) {

    }
}
