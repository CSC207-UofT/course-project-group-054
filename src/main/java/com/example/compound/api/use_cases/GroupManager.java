package com.example.compound.api.use_cases;

import com.example.compound.api.entities.Group;

public class GroupManager {
    /**
     * Add the member to the group.
     * @param group The group to which the member is to be added.
     * @param uuid the uid of the member that is to be added to the group.
     */
    public void addMember(Group group, Integer uuid) {
        group.getMembers().add(uuid);
    }

    /**
     * Remove a member from the group.
     * @param group The group from which the member is to be removed.
     * @param uuid the uid of the member that is to be removed from the group.
     */
    public void removeMember(Group group, Integer uuid) {
        group.getMembers().remove(uuid);
    }

    /**
     * Add an expense to the group.
     * @param group The group to which the expense is to be added.
     * @param euid the uid of the expense that is to be added to the group.
     */
    public void addExpense(Group group, Integer euid) {
        group.getExpenses().add(euid);
    }

    /**
     * Remove an expense from the group.
     * @param group The group from which the expense is to be removed.
     * @param euid the uid of the expense that is to be removed from the group.
     */
    public void removeExpense(Group group, Integer euid) {
        group.getExpenses().remove(euid);
    }
}
