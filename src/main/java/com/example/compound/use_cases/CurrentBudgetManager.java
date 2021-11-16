package com.example.compound.use_cases;

import com.example.compound.entities.Budget;
import com.example.compound.use_cases.gateways.BudgetRepositoryGateway;

/**
 * A use case that stores the Budget being currently used by the program.
 */
public class CurrentBudgetManager {
    private Budget currentBudget;
    private final BudgetRepositoryGateway budgetRepositoryGateway;

    public CurrentBudgetManager(BudgetRepositoryGateway budgetRepositoryGateway) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
    }

    /**
     * Return the UID of the Budget being currently used by the program.
     * @return the UID of the Budget being currently used by the program
     */
    public String getCurrentBudgetUID() {
        return currentBudget.getBUID();
    }

    /**
     * Set the UID of the Budget being currently used by the program to the given value
     * @param BUID the new UID of the Budget that the program is to use
     */
    public void setCurrentBudget(String BUID) {
        this.currentBudget = this.budgetRepositoryGateway.findById(BUID);
    }
}
