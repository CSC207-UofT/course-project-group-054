package com.example.compound.entities;

import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

import static org.junit.Assert.fail;

/**
 * A test class for CostVetoableChangeResponseStrategy.
 */
public class CostVetoableChangeResponseStrategyTest {
    VetoableChangeResponseStrategy strategy;
    Item i1;
    PropertyChangeEvent evt;
    double totalCost;
    double maxSpend;

    @Before
    public void setUp() {
        strategy = new CostVetoableChangeResponseStrategy();
        i1 = new Item("1", "Carrot", 2.00, 1);
        evt = new PropertyChangeEvent(i1, "cost", 2.00, 3.00);
        totalCost = i1.getCost() * i1.getQuantity();
        maxSpend = totalCost;
    }

    @Test
    public void testRespond() {
        try {
            strategy.respond(evt, i1, totalCost, maxSpend);
        } catch (PropertyVetoException e) {
            return;
        }
        fail();
    }
}
