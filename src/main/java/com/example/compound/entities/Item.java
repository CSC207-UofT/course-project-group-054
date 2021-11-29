package com.example.compound.entities;

import java.beans.*;

/**
 * An item for use with the Budget class with a category, name, cost in dollars and quantity.
 */
public class Item {
    private String IUID;
    private String name;
    private double cost;
    private int quantity;

    private final VetoableChangeSupport observableVetoable;
    private final PropertyChangeSupport observableProperty;

    /**
     * Construct a new item with the given UID, name, cost, and quantity.
     * @param IUID     the UID of this Item
     * @param name     the name of this item
     * @param cost     the cost of this item (in dollars)
     * @param quantity the quantity of this item
     */
    public Item(String IUID, String name, double cost, int quantity) {
        this.IUID = IUID;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.observableVetoable = new VetoableChangeSupport(this);
        this.observableProperty = new PropertyChangeSupport(this);
    }

    /**
     * Return this Item's UID.
     * @return this Item's UID
     */
    public String getIUID() {
        return IUID;
    }

    /**
     * Set this Item's UID to the given value.
     * @param IUID the new IUID of this Item
     */
    public void setIUID(String IUID) {
        this.IUID = IUID;
    }

    /**
     * Return this Item's cost.
     * @return this Item's cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Return this Item's name.
     * @return this Item's name
     */
    public String getName() {
        return name;
    }

    /**
     * Return this Item's quantity.
     * @return this Item's quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Make the given VetoableChangeListener object an observer of this Item so that objects containing that object can
     * track and prevent changes in this Item's quantity and cost.
     * @param observer the VetoableChangeListener object that is to be made an observer of this Item
     */
    public void addObserver(VetoableChangeListener observer) {
        observableVetoable.addVetoableChangeListener(observer);
    }

    /**
     * Make the given PropertChangeListener object an observer of this Item so that objects containing that object can
     * track changes in this Item's name.
     * @param observer the PropertyChangeListener object that is to be made an observer of this Item
     */
    public void addObserver(PropertyChangeListener observer) {
        observableProperty.addPropertyChangeListener(observer);
    }

    /**
     * Set this Item's name to the given value.
     * @param newName this Item's new name
     */
    public void setName(String newName) {
        String oldName = this.name;
        this.name = newName;
        observableProperty.firePropertyChange("name", oldName, newName);
    }

    /**
     * Set this Item's cost to the given value.
     * @param newCost this Item's new cost
     * @return whether the Item's cost was set to the given value
     */
    public boolean setCost(double newCost) {
        double oldCost = this.cost;
        try {
            observableVetoable.fireVetoableChange("cost", oldCost, newCost);
        } catch (PropertyVetoException e) {
            return false;
        }
        this.cost = newCost;
        return true;
    }

    /**
     * Set this Item's quantity to the given value.
     * @param newQuantity this Item's new quantity
     * @return whether the Item's quantity was set to the given value
     */
    public boolean setQuantity(int newQuantity) {
        int oldQuantity = this.quantity;
        try {
            observableVetoable.fireVetoableChange("quantity", oldQuantity, newQuantity);
        } catch (PropertyVetoException e) {
            return false;
        }
        this.quantity = newQuantity;
        return true;
    }
}
