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
    User u;
    Group g;

    @Before
    public void setUp() {
        b = new Budget("", "Test", 500.0);
        i1 = new Item("", "Carrot", 2.00, 1);
        i2 = new Item("", "Pickle", 5000.00, 500);
        g = new Group("A", new ArrayList<>(), new ArrayList<>(), "New group");
        u = new User("name", 100.01, "email");
    }

    @Test
    public void testGetBUID() {
        assertEquals("", b.getBUID());
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
        assertEquals(i1, b.getItem("Carrot"));
        b.removeItem("Carrot");
    }

    @Test
    public void testAddItemExceedingMaxSpend() {
        assertFalse(b.addItem(i2));
        assertNull(b.getItem("Item"));
    }

    @Test
    public void testGetItem() {
        b.addItem(i1);
        Item returnedItem = b.getItem("Carrot");
        assertEquals(i1, returnedItem);
        b.removeItem("Carrot");
    }

    @Test
    public void testGetItems() {
        b.addItem(i1);
        Map<String, Item> returnedItems = b.getItems();
        HashMap<String, Item> expected = new HashMap<>();
        expected.put(i1.getName(), i1);
        assertEquals(expected, returnedItems);
        b.removeItem("Carrot");
    }

    @Test
    public void testChangeQuantityWithinMaxSpend() {
        b.addItem(i1);
        boolean returnedBoolean = b.changeQuantity("Carrot", 5);
        assertEquals(5, b.getItem("Carrot").getQuantity());
        assertTrue(returnedBoolean);
        b.removeItem("Carrot");
    }

    @Test
    public void testChangeQuantityExceedingMaxSpend() {
        b.addItem(i1);
        assertFalse(b.changeQuantity("Carrot", 50000));
        b.removeItem("Carrot");
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
    public void testVetoableChange() {
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
    public void testPropertyChange() {
        b.addItem(i1);
        PropertyChangeEvent event = new PropertyChangeEvent(i1, "name", "", "Changed Carrot");
        b.propertyChange(event);
        assertEquals(i1, b.getItems().get("Changed Carrot"));
    }
}