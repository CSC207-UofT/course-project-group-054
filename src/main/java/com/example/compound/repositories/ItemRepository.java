package com.example.compound.repositories;

import com.example.compound.entities.Item;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;

import java.util.List;

public class ItemRepository implements RepositoryGatewayI<Item> {
    @Override
    public Item findByUID(String UID) {
        return null;
    }

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public String save(Item item) {
        return null;
    }

    @Override
    public void deleteById(String UID) {

    }
}
