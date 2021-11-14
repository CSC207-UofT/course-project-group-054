package com.example.compound.use_cases.budget.transfer_data;

import com.example.compound.entities.Item;

public class ItemTransferData {
    private String IUID;
    private String name;
    private double cost;
    private int quantity;

    public ItemTransferData(String IUID, String name, double cost, int quantity) {
        this.IUID = IUID;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
    }

    public ItemTransferData(Item item) {
        this.IUID = item.getIUID();
        this.name = item.getName();
        this.cost = item.getCost();
        this.quantity = item.getQuantity();
    }

    public String getIUID() {
        return IUID;
    }

    public void setIUID(String IUID) {
        this.IUID = IUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item toItem() {
        return new Item(IUID, name, cost, quantity);
    }
}
