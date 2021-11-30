package com.example.compound.cli;

import com.example.compound.controller.*;
import com.example.compound.data.*;
import com.example.compound.repositories.BudgetRepository;
import com.example.compound.repositories.GroupRepository;
import com.example.compound.repositories.ItemRepository;

public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        data.initializeData();

        View view = new View();
        ItemRepository itemRepository = new ItemRepository(); // TODO: RepositoryGatewayI<ItemTransferData> itemRepository = ...?
        BudgetRepository budgetRepository = new BudgetRepository(itemRepository);
        GroupRepository groupRepository = new GroupRepository();
        Controller controller = new Controller(budgetRepository, groupRepository, itemRepository,
                data);

        do {
            controller.menu(view);
        } while (controller.getIsNotLoggedIn());
    }
}
