package com.example.compound.controller;

import com.example.compound.entities.Group;
import com.example.compound.use_cases.*;
import com.example.compound.use_cases.gateways.RepositoryGateway;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.BudgetTransferData;
import com.example.compound.use_cases.transfer_data.GroupTransferData;
import com.example.compound.use_cases.transfer_data.ItemTransferData;

import java.util.ArrayList;
import java.util.List;

public class GroupController {
    private final CurrentGroupManager currentGroupManager;
    private final GroupManager groupManager;
    private final CurrentUserManager currentUserManager;
    private final RepositoryGatewayI<BudgetTransferData> budgetRepository;
    private final RepositoryGatewayI<GroupTransferData> groupRepository;
    private final RepositoryGatewayI<ItemTransferData> itemRepository;
    private final ExpenseManager expenseManager;

    public GroupController(RepositoryGateway repositoryGateway,
                           RepositoryGatewayI<BudgetTransferData> budgetRepository,
                           RepositoryGatewayI<GroupTransferData> groupRepository,
                           RepositoryGatewayI<ItemTransferData> itemRepository,
                           CurrentUserManager currentUserManager, ExpenseManager expenseManager) {
        this.budgetRepository = budgetRepository; // TODO: instantiate gateways here or inject dependencies?
        this.groupRepository = groupRepository;
        this.itemRepository = itemRepository;
        this.currentGroupManager = new CurrentGroupManager(repositoryGateway);
        this.groupManager = new GroupManager(repositoryGateway);
        this.currentUserManager = currentUserManager;
        this.expenseManager = expenseManager;
    }

    public static final String[] groupActions = {
            "Edit Group Name",
            "Add People to Group",
            "Remove People",
            "View GroupMembers",
            "Leave Group",
            "Delete Group",
            "Manage Budgets",
            "Back"
    };

    /**
     * While the users want to update their group(s), have the user choose an action to perform on their group
     * and perform that action.
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
     * A helper method that enables current user to add or remove people to a group.
     * @param inOut The interface for the view
     * @param addOrRemove Add or Remove
     * @return A list of strings of people to be added or removed
     */
    private List<String> addRemovePeople(InOut inOut, String addOrRemove) {
        List<String> people = new ArrayList<>();
        boolean addRemovePeople = Boolean.TRUE;
        do {
            inOut.sendOutput("Do you want to " + addOrRemove + " more people in your "
                    + "group" + "? (y/n)");
            String input2 = inOut.getInput();
            switch (input2) {
                case "y" -> {
                    inOut.sendOutput("Enter user email:");
                    people.add(inOut.getInput());
                }
                case "n" -> addRemovePeople = Boolean.FALSE;
            }
        } while (addRemovePeople);
        return people;
    }


    /**
     * A helper method extracted from the updateGroup method that involves all the optional actions on groups.
     * @param inOut the user interface object
     * @param inputG the option that the user chose
     * @return the while-loop "indicator"
     */
    public boolean manageGroup(InOut inOut, int inputG) {
        boolean back = false;
        switch (inputG){
            case 1 -> {
                inOut.sendOutput("Please enter the new name.");
                String newName = inOut.getInput();
                this.groupManager.setGroupName(currentGroupManager.getCurrentGroupUID(), newName);
            } //Edit Group Name
            case 2 -> {
                List<String> people = addRemovePeople(inOut, "add");
                for (String p: people) {
                    GroupManager.addMember(currentGroupManager.getCurrentGroup(), p);
                }
            } // Add people to the group
            case 3 -> {
                StringBuilder members = this.groupManager.showGroupMembers(currentGroupManager.getCurrentGroupUID());
                if (members.charAt(0) == 'Y') {
                    inOut.sendOutput("You should delete the group instead.");
                    break;
                }
                inOut.sendOutput("Note that invalid name would be automatically ignored.");
                List<String> people = addRemovePeople(inOut, "remove");
                if (people.contains(currentUserManager.getCurrentUser().getName())){
                    people.remove(currentUserManager.getCurrentUser().getName());
                    inOut.sendOutput("You should leave or delete the group instead.");
                }
                for (String p: people) {
                    try {
                        GroupManager.removeMember(currentGroupManager.getCurrentGroup(), p);
                    } catch (Exception ignored) {
                    }
                }
            } //Remove People from the group
            case 4 -> inOut.sendOutput(this.groupManager.showGroupMembers(currentGroupManager.getCurrentGroupUID()));
                        //View GroupMembers
            case 5 -> //TODO: Need to update the balance of the current user.
                    GroupManager.removeMember(currentGroupManager.getCurrentGroup(),
                            currentUserManager.getCurrentUser().getEmail()); //Leave Group
            case 6 -> //TODO: Need to update the balance of all the users in the group.
                    this.groupManager.removeGroup(currentGroupManager.getCurrentGroupUID()); //Delete Group
            case 7 -> new BudgetController(currentGroupManager.getCurrentGroup().getGUID(),
//                    repositoryGateway,
                    budgetRepository, groupRepository, itemRepository,
                    expenseManager).groupBudgetsDashboard(inOut);
            case 8 -> back = true;
        }
        return back;
    }
}
