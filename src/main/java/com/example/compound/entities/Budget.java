package com.example.compound.entities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.*;

/**
 * A budget tracking the items a user is willing to buy in a certain area of their life, along with those items' costs.
 *
 * A budget has a limit on the amount on money that can be spent on items in the budget.
 */
public class Budget implements VetoableChangeListener, PropertyChangeListener {
    private final String BUID;
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
                && !budget.containsKey(item.getName())) {
            budget.put(item.getName(), item);
            item.addObserver((PropertyChangeListener) this); // TODO: Should this be in the Budget class?
            item.addObserver((VetoableChangeListener) this); // TODO: Should this be in the Budget class?
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the item with the given name and of the given category.
     *
     * @param item the name of the item
     * @return the item with the given name and of the given category, or null if the given category or item is not in
     *         this budget
     */
    public Item getItem(String item) {
        return budget.getOrDefault(item, null);
    }

    // TODO: Fix this
//    public Item getItem(String IUID) {
//        for (String itemName : budget.keySet()) {
//            if (budget.get(itemName).getIUID().equals(IUID)) {
//                return budget.get(itemName);
//            }
//        }
//        return null;
//    }

    /**
     * Return a mapping from the names of items in this budget to those Item objects.
     * @return a mapping from the names of items in this budget to those Item objects
     */
    public Map<String, Item> getItems() {
        return budget;
    }

    /**
     * Change the quantity of the item with the given name to the given quantity.
     *
     * @param itemName    the name of the item
     * @param newQuantity the new quantity of the item
     * @return whether the quantity was changed
     */
    public boolean changeQuantity(String itemName, int newQuantity)
            throws NullPointerException {
        Item item = Objects.requireNonNull(getItem(itemName));
        return item.setQuantity(newQuantity);
    }

    /**
     * Remove the item with the given name from this budget.
     *
     * @param item     the name of the item to be removed
     * @return whether the given item was removed
     */
    public boolean removeItem(String item) {
        if (budget.containsKey(item)) {
            budget.remove(item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the total cost of all items in this budget.
     *
     * @return the total cost of all items in this budget
     */
    public double getTotalCost() {
        double totalCost = 0;
        for (String itemName : budget.keySet()) {
            totalCost += budget.get(itemName).getCost() * budget.get(itemName).getQuantity();
        }
        return totalCost;
    }

    /**
     * Return a mapping from item name to the cost of the item as a percentage of the total cost of all items in this
     * budget.
     *
     * @return a mapping from item name to the cost of the item as a percentage of the total cost of all items in this
     *         budget, or null if getTotalCost returns 0
     */
    public HashMap<String, Double> getPercentages() {
        HashMap<String, Double> percentages = new HashMap<>();
        double totalCost = getTotalCost();

        if (totalCost == 0) {
            return null;
        }

        for (String itemName : budget.keySet()) {
            Item item = budget.get(itemName);
            double itemCost = item.getCost() * item.getQuantity();
            percentages.put(itemName, itemCost / totalCost);
        }
        return percentages;
    }

    /**
     * Checks whether the given PropertyChangeEvent relating to an Item object in this Budget satisfies this Budget's
     * constraints, and if not, throws a PropertyVetoException.
     * @param evt the PropertyChangeEvent triggered by a change to an Item in this Budget
     * @throws PropertyVetoException if the requested change would result in this Budget's maxSpend being exceeded
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        switch (evt.getPropertyName()) { // TODO: Is the presence of this switch statement a code smell? Is it needed?
            case "quantity" -> {
                int newQuantity = (Integer) evt.getNewValue();
                Item item = (Item) evt.getSource();
                if (getTotalCost() + (newQuantity - item.getQuantity()) * item.getCost() > maxSpend) {
                    throw new PropertyVetoException("The requested change would result in this Budget's maxSpend being "
                            + "exceeded", evt);
                }
            }
            case "cost" -> {
                double newCost = (Double) evt.getNewValue();
                Item item = (Item) evt.getSource();
                if (getTotalCost() + item.getQuantity() * (newCost - item.getCost()) > maxSpend) {
                    throw new PropertyVetoException("The requested change would result in this Budget's maxSpend being "
                            + "exceeded", evt);
                }
            }
        }
    }

    /**
     * Responds to the given PropertyChangeEvent relating to an Item object in this Budget.
     * @param evt the PropertyChangeEvent triggered by a change to an Item in this Budget
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("name")) {
            Item item = (Item) evt.getSource();
            budget.remove(item.getName());
            budget.put((String) evt.getNewValue(), item);
        }
    }
}