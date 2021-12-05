package com.example.compound.entities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

/**
 * A strategy interface that provides a Budget with a response to a vetoable change in an attribute of an Item it
 * contains.
 */
interface VetoableChangeResponseStrategy {
    /**
     * Respond to a vetoable change in the given Item's attribute.
     * @param evt       the event object representing a change in an attribute of the given Item
     * @param item      the Item whose attribute has changed
     * @param totalCost the total cost of all the items in the Budget that is to respond to the vetoable change
     * @param maxSpend  the limit on spending on the items in the Budget that is to respond to the vetoable change
     * @throws PropertyVetoException if the vetoable change would result in the Budget's spending limit being exceeded
     */
    void respond(PropertyChangeEvent evt, Item item, double totalCost, double maxSpend) throws PropertyVetoException;
}
