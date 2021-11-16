package com.example.compound.use_cases.gateways;

import com.example.compound.entities.Item;

import java.util.List;

public interface ItemRepositoryGateway {
    Item findById(String IUID);

    List<Item> findAll();

    // Returns a new IUID
    String save(Item item);

    void deleteById(String IUID);

//    // ...
//
//    Item loadItem(String IUID);
//
//    // boolean save(Item item);
//
//    boolean createItem(Item item); // TODO: Create a new data transfer object (see https://piazza.com/class/kt4hlydpsym1bz?cid=843)
//
//    boolean changeQuantity(Item item, int newQuantity);
//
//    boolean deleteItem(String IUID);
}
