package com.example.compound.entities;

import com.example.compound.entities.Budget;
import com.example.compound.entities.Item;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BudgetTest {
    Budget b;
    Item i;
    User u;
    Group g;

    @Before
    public void setUp() {
        b = new Budget("", "Test", 500.0);
        i = new Item("", "Carrot", 2.00, 1);
        g = new Group("A", new ArrayList<>(), new ArrayList<>(), "New group");
        u = new User("name", 100.01, "email");
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
    public void testAddItem() {
        boolean returnedBoolean = b.addItem(i);
        assertEquals(i, b.getItem("Carrot"));
        assertTrue(returnedBoolean);
        b.removeItem("Carrot");
    }

    @Test
    public void testGetItem() {
        b.addItem(i);
        Item returnedItem = b.getItem("Carrot");
        assertEquals(i, returnedItem);
        b.removeItem("Carrot");
    }

    @Test
    public void testChangeQuantity() {
        b.addItem(i);
        boolean returnedBoolean = b.changeQuantity("Carrot", 5);
        assertEquals(5, b.getItem("Carrot").getQuantity());
        assertTrue(returnedBoolean);
        b.removeItem("Carrot");
    }

    @Test
    public void testGetTotalCost() {
        b.addItem(i);
        double totalCost = b.getTotalCost();
        assertEquals(2.00, totalCost, 0.01);
    }

    @Test
    public void testGetPercentages() {
        b.addItem(i);
        HashMap<String, Double> percentages = b.getPercentages();
        HashMap<String, Double> expected = new HashMap<>();
        expected.put("Carrot", 1.0);
        assertEquals(expected, percentages);
    }

    @Test
    public void testRemoveItem() {
        b.addItem(i);
        boolean returnedBoolean = b.removeItem("Carrot");
        assertNull(b.getItem("Carrot"));
        assertTrue(returnedBoolean);
    }
}