package com.example.compound.entities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

/**
 * A strategy class that provides a Budget with a response to a vetoable change in the cost of an Item it contains.
 */
public class CostVetoableChangeResponseStrategy implements VetoableChangeResponseStrategy {
    /**
     * Respond to a vetoable change in the given Item's cost.
     * @param evt       the event object representing a change in the given Item's cost
     * @param item      the Item whose cost has changed
     * @param totalCost the total cost of all the items in the Budget that is to respond to the vetoable change
     * @param maxSpend  the limit on spending on the items in the Budget that is to respond to the vetoable change
     * @throws PropertyVetoException if the vetoable change would result in the Budget's spending limit being exceeded
     */
    @Override
    public void respond(PropertyChangeEvent evt, Item item, double totalCost, double maxSpend)
            throws PropertyVetoException {
        double newCost = (Double) evt.getNewValue();
        if (totalCost + item.getQuantity() * (newCost - item.getCost()) > maxSpend) {
            throw new PropertyVetoException("The requested change to this Budget's cost would result in this Budget's "
                    + "spending limit being exceeded", evt);
        }
    }
}
