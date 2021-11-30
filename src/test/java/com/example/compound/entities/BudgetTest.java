package com.example.compound.entities;

import org.junit.*;

import static org.junit.Assert.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BudgetTest {
    Budget b;
    Item i1;
    Item i2;
    Group g;

    @Before
    public void setUp() {
        b = new Budget("1", "Test", 500.0);
        i1 = new Item("1", "Carrot", 2.00, 1);
        i2 = new Item("2", "Pickle", 5000.00, 500);
        g = new Group("A", new ArrayList<>(), new ArrayList<>(), "New group");
    }

    @Test
    public void testGetBUID() {
        assertEquals("1", b.getBUID());
    }

    @Test
    public void testSetBUID() {
        b.setBUID("2");
        assertEquals("2", b.getBUID());
    }

    @Test
    public void testGetName() {
        assertEquals("Test", b.getName());
    }

    @Test
    public void testSetName() {
        b.setName("Test2");
        assertEquals("Test2", b.getName());
    }

    @Test
    public void testGetMaxSpend() {
        assertEquals(500.0, b.getMaxSpend(), 0.01);
    }

    @Test
    public void testSetMaxSpend() {
        b.setMaxSpend(600.0);
        assertEquals(600.0, b.getMaxSpend(), 0.01);
    }

    @Test
    public void testAddItemWithinMaxSpend() {
        assertTrue(b.addItem(i1));
        assertEquals(i1, b.getItemByName("Carrot"));
        b.removeItem(i1.getIUID());
    }

    @Test
    public void testAddItemExceedingMaxSpend() {
        assertFalse(b.addItem(i2));
        assertNull(b.getItemByName("Item"));
    }

    @Test
    public void testGetItemByIUID() {
        b.addItem(i1);
        Item returnedItem = b.getItemByIUID("1");
        assertEquals(i1, returnedItem);
        b.removeItem(i1.getIUID());
    }
    @Test
    public void testGetItemByName() {
        b.addItem(i1);
        Item returnedItem = b.getItemByName("Carrot");
        assertEquals(i1, returnedItem);
        b.removeItem(i1.getIUID());
    }

    @Test
    public void testGetItems() {
        b.addItem(i1);
        Map<String, Item> returnedItems = b.getItems();
        HashMap<String, Item> expected = new HashMap<>();
        expected.put(i1.getIUID(), i1);
        assertEquals(expected, returnedItems);
        b.removeItem(i1.getIUID());
    }

    @Test
    public void testChangeQuantityWithinMaxSpend() {
        b.addItem(i1);
        boolean returnedBoolean = b.changeQuantity("Carrot", 5);
        assertEquals(5, b.getItemByName("Carrot").getQuantity());
        assertTrue(returnedBoolean);
        b.removeItem(i1.getIUID());
    }

    @Test
    public void testChangeQuantityExceedingMaxSpend() {
        b.addItem(i1);
        assertFalse(b.changeQuantity("Carrot", 50000));
        b.removeItem(i1.getIUID());
    }

    @Test
    public void testRemoveItem() {
        b.addItem(i1);
        boolean returnedBoolean = b.removeItem(i1.getIUID());
        assertNull(b.getItemByName("Carrot"));
        assertTrue(returnedBoolean);
    }

    @Test
    public void testGetTotalCost() {
        b.addItem(i1);
        double totalCost = b.getTotalCost();
        assertEquals(2.00, totalCost, 0.01);
    }

    @Test
    public void testRemoveItem() {
        b.addItem(i1);
        boolean returnedBoolean = b.removeItem("Carrot");
        assertNull(b.getItem("Carrot"));
        assertTrue(returnedBoolean);
    }

    @Test
    public void testVetoableChangeQuantity() {
        b.addItem(i1);
        PropertyChangeEvent event = new PropertyChangeEvent(i1, "quantity", 1, 5000);
        try {
            b.vetoableChange(event);
        } catch (PropertyVetoException e) {
            return;
        }
        fail();
    }

    @Test
    public void testVetoableChangeCost() {
        b.addItem(i1);
        PropertyChangeEvent event = new PropertyChangeEvent(i1, "cost", 2.00, 5000.0);
        try {
            b.vetoableChange(event);
        } catch (PropertyVetoException e) {
            return;
        }
        fail();
    }
}