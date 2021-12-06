package com.example.compound.entities;

import java.beans.VetoableChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.*;

/**
 * A budget tracking the items a user is willing to buy in a certain area of their life, along with those items' costs.
 *
 * A budget has a limit on the amount on money that can be spent on items in the budget.
 */
public class Budget implements VetoableChangeListener {
    private String BUID;
    private String name;
    private double maxSpend;
    private final Map<String, Item> budget;

    /**
     * Construct a new budget with the given limit on spending.
     *
     * @param maxSpend the maximum amount of money that can be spent on items in this budget in timeSpan days
     */
    public Budget(String BUID, String name, double maxSpend) {
        this.BUID = BUID;
        this.name = name;
        this.maxSpend = maxSpend;
        budget = new HashMap<>();
    }

    /**
     * Return the unique ID of this budget.
     * @return the unique ID of this budget
     */
    public String getBUID() {
        return BUID;
    }

    /**
     * Set this budget's BUID to the given value.
     * @param BUID this budget's new BUID
     */
    public void setBUID(String BUID) {
        this.BUID = BUID;
    }

    /**
     * Return the name of this budget.
     * @return the name of this budget
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this budget to the given value.
     * @param name this budget's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the maximum amount of money that can be spent on items in this budget.
     *
     * @return the maximum amount of money that can be spent on items in this budget
     */
    public double getMaxSpend() {
        return maxSpend;
    }

    /**
     * Set the maximum amount of money that can be spent on items in this budget to the given value.
     *
     * @param maxSpend the maximum amount on money that can be spent on items in this budget
     */
    public void setMaxSpend(double maxSpend) {
        this.maxSpend = maxSpend;
    }

    /**
     * Add the given item to the budget if the item is not in the budget and if adding the item would not result in the
     * budget's total cost exceeding maxSpend.
     *
     * @param item     the item to be added
     * @return whether the given item was added
     */
    public boolean addItem(Item item) {
        // If adding item to this budget would result in this budget's total cost exceeding maxSpend, do not add item
        if ((this.getTotalCost() + item.getQuantity() * item.getCost() <= this.maxSpend)
                && (getItemByIUID(item.getIUID()) == null) && (getItemByName(item.getName()) == null)) {
            budget.put(item.getIUID(), item);
            item.addObserver(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the item with the given UID.
     * @param IUID the UID of the item
     * @return the item with the given UID, or null if the given item is not in this budget
     */
    public Item getItemByIUID(String IUID) {
        return budget.getOrDefault(IUID, null);
    }

    /**
     * Return the item with the given name.
     * @param name the name of the item
     * @return the item with the given name, or null if the given item is not in this budget
     */
    public Item getItemByName(String name) {
        for (String itemIUID : budget.keySet()) {
            if (budget.get(itemIUID).getName().equals(name)) {
                return budget.get(itemIUID);
            }
        }
        return null;
    }

    /**
     * Return a mapping from the names of items in this budget to those Item objects.
     * @return a mapping from the names of items in this budget to those Item objects
     */
    public Map<String, Item> getItems() {
        return budget;
    }

    /**
     * Change the quantity of the item with the given name to the given quantity.
     * @param itemName    the name of the item
     * @param newQuantity the new quantity of the item
     * @return whether the quantity was changed
     */
    public boolean changeQuantity(String itemName, int newQuantity) throws NullPointerException {
        Item item = Objects.requireNonNull(getItemByName(itemName));
        return item.setQuantity(newQuantity);
    }

    /**
     * Remove the item with the given IUID from this budget.
     * @param IUID     the IUID of the item to be removed
     * @return whether the given item was removed
     */
    public boolean removeItem(String IUID) {
        if (budget.containsKey(IUID)) {
            budget.remove(IUID);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the total cost of all items in this budget.
     * @return the total cost of all items in this budget
     */
    public double getTotalCost() {
        double totalCost = 0;
        for (String itemIUID : budget.keySet()) {
            totalCost += budget.get(itemIUID).getCost() * budget.get(itemIUID).getQuantity();
        }
        return totalCost;
    }

    /**
     * Checks whether the given PropertyChangeEvent relating to an Item object in this Budget satisfies this Budget's
     * constraints, and if not, throws a PropertyVetoException.
     * @param evt the PropertyChangeEvent triggered by a change to an Item in this Budget
     * @throws PropertyVetoException if the requested change would result in this Budget's maxSpend being exceeded
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        VetoableChangeResponseStrategy strategy;
        Item item = (Item) evt.getSource();
        double totalCost = getTotalCost();
        switch (evt.getPropertyName()) {
            case "quantity" -> {
                /*
                 If an Item's quantity was changed, ensure that the change does not exceed this Budget's spending
                 limit by throwing an exception otherwise
                */
                strategy = new QuantityVetoableChangeResponseStrategy();
                strategy.respond(evt, item, totalCost, maxSpend);
            }
            case "cost" -> {
                /*
                 If an Item's cost was changed, ensure that the change does not exceed this Budget's spending limit by
                 throwing an exception otherwise
                */
                strategy = new CostVetoableChangeResponseStrategy();
                strategy.respond(evt, item, totalCost, maxSpend);
            }
        }
    }
}
