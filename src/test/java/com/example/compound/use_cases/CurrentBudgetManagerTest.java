package com.example.compound.use_cases;

//import com.example.compound.data.Data;
import com.example.compound.entities.Group;
import com.example.compound.repositories.BudgetRepository;
import com.example.compound.repositories.GroupRepository;
import com.example.compound.repositories.ItemRepository;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;
//import com.example.compound.use_cases.gateways.RepositoryGateway;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;
import com.example.compound.use_cases.transfer_data.GroupTransferData;
import com.example.compound.use_cases.transfer_data.ItemTransferData;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * A test class for CurrentBudgetManager. The tests in this class should pass when the repository gateways are
 * implemented.
 */
public class CurrentBudgetManagerTest {
    RepositoryGatewayI<BudgetTransferData> budgetRepositoryGateway;
    RepositoryGatewayI<GroupTransferData> groupRepositoryGateway;
    RepositoryGatewayI<ItemTransferData> itemRepositoryGateway;
//    RepositoryGateway repositoryGateway;
    BudgetManager budgetManager;
    GroupTransferData g;
    String BUID;
    CurrentBudgetManager currentBudgetManager;

    @Before
    public void setUp() {
        budgetRepositoryGateway = new BudgetRepository();
        groupRepositoryGateway = new GroupRepository();
        itemRepositoryGateway = new ItemRepository();
//        repositoryGateway = new Data();

        g = new GroupTransferData("A", new ArrayList<>(), new ArrayList<>(), "New group");

        budgetManager = new BudgetManager(budgetRepositoryGateway, groupRepositoryGateway, itemRepositoryGateway);
//        budgetManager = new BudgetManager(repositoryGateway);
        groupRepositoryGateway.save(g);
//        repositoryGateway.addGroup(g);

        budgetManager.create(g.getGUID(), "name", 3.0);
        BUID = budgetManager.getBUIDFromName("name");

        currentBudgetManager = new CurrentBudgetManager(budgetRepositoryGateway);
//        currentBudgetManager = new CurrentBudgetManager(repositoryGateway);
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
        currentBudgetManager.setCurrentBudget(newBUID);
        assertEquals(newBUID, currentBudgetManager.getCurrentBudgetUID());
    }
}