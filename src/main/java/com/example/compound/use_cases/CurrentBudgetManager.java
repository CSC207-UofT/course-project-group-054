package com.example.compound.use_cases;

import com.example.compound.entities.Budget;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;
//import com.example.compound.use_cases.gateways.RepositoryGateway;

/**
 * A use case that stores the Budget currently being used by the program.
 */
public class CurrentBudgetManager {
    private Budget currentBudget;
    private final RepositoryGatewayI<BudgetTransferData> budgetRepositoryGateway;
//    private final RepositoryGateway repositoryGateway;

    /**
     * Construct a new CurrentBudgetManager with the given parameter.
     * @param budgetRepositoryGateway the repository for budgets
     */
    public CurrentBudgetManager(RepositoryGatewayI<BudgetTransferData> budgetRepositoryGateway
//                                RepositoryGateway repositoryGateway
    ) {
        this.budgetRepositoryGateway = budgetRepositoryGateway;
//        this.repositoryGateway = repositoryGateway;
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
        this.currentBudget = this.budgetRepositoryGateway.findByUID(BUID).toBudget();
//        this.currentBudget = this.repositoryGateway.findByBUID(BUID);
    }
}
