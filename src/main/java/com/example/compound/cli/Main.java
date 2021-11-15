package com.example.compound.cli;

import com.example.compound.controller.*;
import com.example.compound.data.*;
import com.example.compound.use_cases.gateways.BudgetRepositoryGateway;
import com.example.compound.use_cases.gateways.ItemRepositoryGateway;
import com.example.compound.use_cases.gateways.GroupRepositoryGateway;

public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        data.initializeData();

        BudgetRepositoryGateway budgetRepositoryGateway = null; // TODO: Implement the gateway
        GroupRepositoryGateway groupRepositoryGateway = null;
        ItemRepositoryGateway itemRepositoryGateway = null;

        View view = new View();
        Controller controller = new Controller(budgetRepositoryGateway, groupRepositoryGateway, itemRepositoryGateway,
                data);

        do {
            controller.menu(view);

        } while (controller.getIsNotLoggedIn());
    }
}
