package com.example.compound.use_cases.gateways;

import com.example.compound.entities.Item;

import java.util.List;

public interface ItemRepositoryGateway {
    Item findById(String IUID);

    List<Item> findAll();

    // Returns a new IUID
    String save(Item item);

    void deleteById(String IUID);
}
