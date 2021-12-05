package com.example.compound.use_cases;

import com.example.compound.entities.Expense;
import com.example.compound.entities.Group;
import com.example.compound.entities.Person;
import com.example.compound.entities.User;
import com.example.compound.use_cases.gateways.RepositoryGateway;

import java.util.ArrayList;
import java.util.List;

/*
This is the manager for Group, we edit the entity group through this class.
 */
public class GroupManager {
    private final RepositoryGateway repositoryGateway;

    public GroupManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }

    /**
     * Remove the group with the given GUID
     * @param GUID the GUID of the Group to be removed
     */
    public void removeGroup(String GUID) {
        List<Group> groups = repositoryGateway.getGroups();
        for (Group group : groups) {
            if (group.getGUID().equals(GUID)) {
                this.repositoryGateway.removeGroup(group);
            }
        }
    }

    /**
     * Print the given person's group(s) in a list.
     * @param p the person who needs to show its group(s)
     * @return the group(s) that the person p has.
     *         Return "You don't have any groups now.\n" if the person has no groups now.
     */
    public StringBuilder showListOfGroup(Person p) {
        StringBuilder lst = new StringBuilder("List of groups:\n");
        int counter = 0;
        for (Group g: repositoryGateway.getGroups()) {
            if (g.getGroupMembers().contains(p.getEmail())) {
                lst.append(g);
                lst.append("\n");
                counter++;
            } //
        }

        if (counter > 0) {
            return lst;
        }

        return new StringBuilder("You don't have any groups now.\n");
    }

    /**
     * Get the list of group(s) the person p has.
     * @param p the person who has the group(s)
     * @return the list of group(s) the person p has.
     *         Return empty list if the person has no groups now.
     */
    public List<String> getListOfGroup(Person p){
        List<String> lst = new ArrayList<>();
        for (Group g: repositoryGateway.getGroups()) {
            if (g.getGroupMembers().contains(p.getEmail())) {
                lst.add(g.getGroupName());
            } //
        }
        return lst;
    }

    /**
     * Remove the member from the group.
     * @param g The group from which the member is to be removed.
     * @param email the identifier of the member to remove.
     */
    public static void removeMember(Group g, String email) {
        g.removeMember(email);
    }

    /**
     * Add the member to the group.
     * @param g The group to which the member is to be added.
     * @param email the email of the member that is to be added to the group.
     */
    public static void addMember(Group g, String email) {
        g.addMember(email);
    }

    /**
     * Set the name of the group with a new name.
     * @param GUID The GUID of the group.
     * @param newName The new name of the group.
     */
    public void setGroupName(String GUID, String newName) {
        List<Group> groups = repositoryGateway.getGroups();
        for (Group group : groups) {
            if (group.getGUID().equals(GUID)) {
                group.setGroupName(newName);
            }
        }
    }

    /**
     * Print the list of members in the group.
     * @param GUID The GUID of the group
     * @return the list of group members in a string.
     *         Return "You are the only one in the group.\n" if the group contains only one user/person.
     */
    public StringBuilder showGroupMembers(String GUID){
        List<Group> groups = repositoryGateway.getGroups();
        for (Group group : groups) {
            if (group.getGUID().equals(GUID)) {
                StringBuilder lst = new StringBuilder("List of group members:\n");
                int counter = 0;
                for (String u: group.getGroupMembers()) {
                    counter++;
                    lst.append(counter).append(": ").append(u).append(".\n");
                }

                if (counter > 1) {
                    return lst;
                }

                return new StringBuilder("You are the only one in the group.\n");
            }
        }
        return null;
    }

    /**
     * Create a new group.
     * @param groupName The name of the group.
     * @param groupMembers The list of members in the group.
     * @param expenseList The list of expenses in the group
     * @param description The description of the group.
     */
    public void createGroup(String groupName, List<String> groupMembers,
                            List<Expense> expenseList, String description) {
        this.repositoryGateway.addGroup(new Group(groupName, groupMembers, expenseList, description));
    }

    /**
     * Return the GUID of the group with the given name.
     * @param name the name of the group
     * @return the GUID of the group with the given name
     */
    public String getGUIDFromName(String name) {
        List<Group> groups = repositoryGateway.getGroups();
        for (Group group : groups) {
            if (group.getGroupName().equals(name)) {
                return group.getGUID();
            }
        }
        return null;
    }

}
