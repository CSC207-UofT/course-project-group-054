package com.example.compound.use_cases;

import com.example.compound.data.Data;
import com.example.compound.entities.Group;
//import com.example.compound.use_cases.gateways.BudgetRepositoryGateway;
//import com.example.compound.use_cases.gateways.GroupRepositoryGateway;
//import com.example.compound.use_cases.gateways.ItemRepositoryGateway;
import com.example.compound.use_cases.gateways.RepositoryGateway;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * A test class for BudgetManager. The tests in this class should pass when the repository gateways are implemented.
 */
public class BudgetManagerTest {
//    BudgetRepositoryGateway budgetRepositoryGateway;
//    GroupRepositoryGateway groupRepositoryGateway;
//    ItemRepositoryGateway itemRepositoryGateway;
    BudgetManager budgetManager;
    ExpenseManager expenseManager;
    RepositoryGateway repositoryGateway; // TODO: Remove this when ExpenseManager uses an ExpenseRepositoryGateway
    Group g;
    String BUID;

    @Before
    public void setUp() {
        // TODO: Implement the gateways
//        budgetRepositoryGateway = null;
//        groupRepositoryGateway = null;
//        itemRepositoryGateway = null;
        repositoryGateway = new Data();

        g = new Group("A", new ArrayList<>(), new ArrayList<>(), "New group");

//        budgetManager = new BudgetManager(budgetRepositoryGateway, groupRepositoryGateway, itemRepositoryGateway);
        budgetManager = new BudgetManager(repositoryGateway);
//        groupRepositoryGateway.save(g);
        repositoryGateway.addGroup(g);

        budgetManager.create(g.getGUID(), "name", 300.0);
        BUID = budgetManager.getBUIDFromName("name");

        repositoryGateway = new Data();
        expenseManager = new ExpenseManager(repositoryGateway);
    }

    @Test
    public void testCreate() {
        assertTrue(budgetManager.create(g.getGUID(), "name2", 5.0));
    }

    @Test
    public void testGetBUIDFromName() {
        assertNotNull(budgetManager.getBUIDFromName("name"));
    }

    @Test
    public void testGetItems() {
        assertEquals(new ArrayList<>(), budgetManager.getItems(BUID));
    }

    @Test
    public void testGetIUIDFromName() {
        budgetManager.addItem(BUID, "itemName", 5.00, 6);
        assertNotNull(budgetManager.getIUIDFromName("itemName"));
    }

    @Test
    public void testToExpenses() {
        assertEquals(new ArrayList<>(), budgetManager.toExpenses(BUID, new ExpenseManager(null)));
    }

    @Test
    public void testAddItem() {
        budgetManager.addItem(BUID, "itemName", 5.00, 6);
        assertEquals("itemName", budgetManager.getItems(BUID).get(0));
    }

    @Test
    public void testChangeItemQuantity() {
        String IUID = budgetManager.addItem(BUID, "itemName", 5.00, 6);
        assertTrue(budgetManager.changeItemQuantity(IUID, 5));
    }

    @Test
    public void testRemoveItem() {
        String IUID = budgetManager.addItem(BUID, "itemName", 5.00, 6);
        assertTrue(budgetManager.removeItem(IUID));
    }

    @Test
    public void testGetMaxSpend() {
        assertEquals(300.0, budgetManager.getMaxSpend(BUID), 0.01);
    }

    @Test
    public void testSetMaxSpend() {
        budgetManager.setMaxSpend(BUID, 5.0);
        assertEquals(5.0, budgetManager.getMaxSpend(BUID), 0.01);
    }

    @Test
    public void testGetPercentages() {
        budgetManager.addItem(BUID, "itemName", 5.00, 6);
        assertEquals(1.0, budgetManager.getPercentages(BUID).get("itemName"), 0.01);
    }

    @Test
    public void testRemove() {
        assertTrue(budgetManager.remove(g.getGUID(), BUID));
    }

    @Test
    public void testAddExpensesToGroup() {
        budgetManager.addExpensesToGroup(g.getGUID(), BUID, expenseManager);
        assertEquals(new ArrayList<>(), g.getExpenseList());
    }

    @Test
    public void testGetBudgetNameList() {
        assertEquals("name", budgetManager.getBudgetNameList(g.getGUID()).get(0));
    }
}