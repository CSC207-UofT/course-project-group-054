package com.example.compound.cli_controllers;

import com.example.compound.repositories.GroupRepository;
import com.example.compound.use_cases.*;
import com.example.compound.use_cases.gateways.RepositoryGateway;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;
import com.example.compound.use_cases.transfer_data.ItemTransferData;

/**
 * A Controller managing input and output for Group use cases.
 */
public class GroupController {
    private final CurrentGroupManager currentGroupManager;
    private final GroupManager groupManager;
    private final CurrentUserManager currentUserManager;
    private final RepositoryGateway repositoryGateway;
    private final ExpenseManager expenseManager;
    private static final String[] groupActions = {
            "Edit Group Name",
            "Add People to Group",
            "Remove People",
            "View Group Members",
            "Leave Group",
            "Delete Group",
            "Manage Budgets",
            "Back"
    };

    /**
     * Construct a new GroupController with the given parameters.
     * @param repositoryGateway  the repository for all objects
     * @param budgetRepository   the repository for budgets
     * @param groupRepository    the repository for groups
     * @param itemRepository     the repository for items
     * @param currentUserManager the use case object storing the current user
     * @param expenseManager     the use case for expenses
     */
    public GroupController(RepositoryGateway repositoryGateway,
                           RepositoryGatewayI<BudgetTransferData> budgetRepository,
                           GroupRepository groupRepository,
                           RepositoryGatewayI<ItemTransferData> itemRepository,
                           CurrentUserManager currentUserManager, ExpenseManager expenseManager) {
        this.currentGroupManager = new CurrentGroupManager(repositoryGateway);
        this.groupManager = new GroupManager(repositoryGateway);
        this.currentUserManager = currentUserManager;
        this.expenseManager = expenseManager;
        this.repositoryGateway = repositoryGateway;
    }

    /**
     * While the users want to update their group(s), have the user choose an action to
     * perform on their group and perform that action.
     * @param inOut the user interface object
     */
    public void updateGroup(InOut inOut) {
        boolean back = false;
        while(!back) {
            StringBuilder lst = groupManager.showListOfGroup(currentUserManager.getCurrentUser());
            inOut.sendOutput(lst);
            if (lst.charAt(0) == 'Y') {
                break;
            }
            inOut.sendOutput("Please select the group you wish to edit.");
            String groupName = inOut.getInput();
            if (!groupManager.getListOfGroup(currentUserManager.getCurrentUser()).contains(groupName)){
                inOut.sendOutput("Please enter a valid group name.\n");
                break;
            }
            String GUID = groupManager.getGUIDFromName(groupName);
            currentGroupManager.setCurrentGroup(GUID);
            int inputG = inOut.getOptionView(groupActions);
            back = manageGroup(inOut, inputG);
        }

    }

    /**
     * A helper method extracted from the updateGroup method that involves all the optional actions on groups.
     * @param inOut the user interface object
     * @param inputG the option that the user chose
     * @return the while-loop "indicator"
     */
    public boolean manageGroup(InOut inOut, int inputG) {
        boolean back = false;
        switch (inputG) {
            // Edit Group Name
            case 1 -> {
                inOut.sendOutput("Please enter the new name.");
                String newName = inOut.getInput();
                this.groupManager.setGroupName(currentGroupManager.getCurrentGroupUID(), newName);
            }
            // Add people to the group
            case 2 -> {
                StringBuilder members = this.groupManager.showGroupMembers(currentGroupManager.getCurrentGroupUID());
                inOut.sendOutput("Note that invalid name would be automatically ignored.");
                inOut.sendOutput("Please enter their email");
                String emailToAdd = inOut.getInput();
                GroupManager.addMember(currentGroupManager.getCurrentGroup(), emailToAdd);
            }
            // Remove people from the group
            case 3 -> {
                inOut.sendOutput("Please enter their email");
                String emailToRemove = inOut.getInput();
                GroupManager.removeMember(currentGroupManager.getCurrentGroup(), emailToRemove);
            }
            // View group members
            case 4 -> {
                for (String email : currentGroupManager.getCurrentGroup().getGroupMembers()) {
                    inOut.sendOutput(email);
                }
            }
            // Leave Group
            case 5 -> //TODO: Need to update the balance of the current user.
                    GroupManager.removeMember(currentGroupManager.getCurrentGroup(),
                            currentUserManager.getCurrentUser().getEmail()); //Leave Group
            case 6 -> //TODO: Need to update the balance of all the users in the group.
                    this.groupManager.removeGroup(currentGroupManager.getCurrentGroupUID()); //Delete Group
            case 7 -> new BudgetController(currentGroupManager.getCurrentGroup().getGUID(),
                    repositoryGateway,
//                    budgetRepository, groupRepository, itemRepository,
                    expenseManager).groupBudgetsDashboard(inOut);
            case 8 -> back = true;
        }
        return back;
    }
}
