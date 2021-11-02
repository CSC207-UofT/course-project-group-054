package entities;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BudgetTest {
    Budget b;
    Item i;
    Group g;

    @Before
    public void setUp() {
        String[] categories = {"Vegetables", "Meat"};
        b = new Budget("Test", categories, 500.0, 30);
        i = new Item("Vegetables", "Carrot", 2.00, 1);
        g = new Group("A", new ArrayList<>(), new ArrayList<>(), "New group");
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
    public void testGetTimeSpan() {
        assertEquals(30, b.getTimeSpan());
    }

    @Test
    public void testSetTimeSpan() {
        b.setTimeSpan(7);
        assertEquals(7, b.getTimeSpan());
    }

    @Test
    public void testAddItem() {
        boolean returnedBoolean = b.addItem("Vegetables", i);
        assertEquals(i, b.getItem("Vegetables", "Carrot"));
        assertTrue(returnedBoolean);
        b.removeItem("Vegetables", "Carrot");
    }

    @Test
    public void testGetItem() {
        b.addItem("Vegetables", i);
        Item returnedItem = b.getItem("Vegetables", "Carrot");
        assertEquals(i, returnedItem);
        b.removeItem("Vegetables", "Carrot");
    }

    @Test
    public void testChangeQuantity() {
        b.addItem("Vegetables", i);
        boolean returnedBoolean = b.changeQuantity("Vegetables", "Carrot", 5);
        assertEquals(5, b.getItem("Vegetables", "Carrot").getQuantity());
        assertTrue(returnedBoolean);
        b.removeItem("Vegetables", "Carrot");
    }

    @Test
    public void testGetNumItems() {
        b.addItem("Vegetables", i);
        int numItems = b.getNumItems("Vegetables");
        assertEquals(1, numItems);
    }

    @Test
    public void testGetTotalCost() {
        b.addItem("Vegetables", i);
        double totalCost = b.getTotalCost();
        assertEquals(2.00, totalCost, 0.01);
    }

    @Test
    public void testGetPercentages() {
        b.addItem("Vegetables", i);
        HashMap<String, Double> percentages = b.getPercentages();
        HashMap<String, Double> expected = new HashMap<>();
        expected.put("Vegetables", 1.0);
        expected.put("Meat", 0.0);
        assertEquals(expected, percentages);
    }

    @Test
    public void testRemoveItem() {
        b.addItem("Vegetables", i);
        boolean returnedBoolean = b.removeItem("Vegetables", "Carrot");
        assertNull(b.getItem("Vegetables", "Carrot"));
        assertTrue(returnedBoolean);
    }

    @Test
    public void testToExpenses() {
        List<Expense> expenses = b.toExpenses(g);
        assertEquals(new ArrayList<Expense>(), expenses);
    }
}