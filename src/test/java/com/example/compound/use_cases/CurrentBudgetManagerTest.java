package com.example.compound.use_cases;

import com.example.compound.entities.Group;
import com.example.compound.use_cases.gateways.BudgetRepositoryGateway;
import com.example.compound.use_cases.gateways.GroupRepositoryGateway;
import com.example.compound.use_cases.gateways.ItemRepositoryGateway;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * A test class for Current BudgetManager. The tests in this class should pass when the repository gateways are
 * implemented.
 */
public class CurrentBudgetManagerTest {
    BudgetRepositoryGateway budgetRepositoryGateway;
    GroupRepositoryGateway groupRepositoryGateway;
    ItemRepositoryGateway itemRepositoryGateway;
    BudgetManager budgetManager;
    Group g;
    String BUID;
    CurrentBudgetManager currentBudgetManager;

    @Before
    public void setUp() {
        // TODO: Implement the gateways
        budgetRepositoryGateway = null;
        groupRepositoryGateway = null;
        itemRepositoryGateway = null;

        g = new Group("A", new ArrayList<>(), new ArrayList<>(), "New group");

        budgetManager = new BudgetManager(budgetRepositoryGateway, groupRepositoryGateway, itemRepositoryGateway);
        groupRepositoryGateway.save(g);

        budgetManager.create(g.getGUID(), "name", 3.0);
        BUID = budgetManager.getBUIDFromName("name");

        currentBudgetManager = new CurrentBudgetManager(budgetRepositoryGateway);
        currentBudgetManager.setCurrentBudget(BUID);
    }

    @Test
    public void testGetCurrentBudgetUID() {
        assertEquals(BUID, currentBudgetManager.getCurrentBudgetUID());
    }

    @Test
    public void testSetCurrentBudget() {
        budgetManager.create(g.getGUID(), "second", 5.0);
        String newBUID = budgetManager.getBUIDFromName("second");
        currentBudgetManager.setCurrentBudget(BUID);
        assertEquals(newBUID, currentBudgetManager.getCurrentBudgetUID());
    }
}