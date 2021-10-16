/*
 * Below is the abstract Group class which represents the origin of our program.
 */
package Entities;

import javax.swing.*;
import java.util.*;



public class Group {
    protected String groupName;
    protected List<String> groupMembers;
    protected List<Expense> expenseList;
    protected String description;
    protected String GUID;

    /**
     *Construct a group with groupName, groupMembers, expenseList, and description.
     * @param groupName the name of the group
     * @param groupMembers the members in the group
     * @param expenseList the list of expenses in the group
     * @param description the description of the group
     */
    public Group(String groupName, List<String> groupMembers, List<Expense> expenseList, String description){
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.expenseList = expenseList;
        this.description = description;
        this.GUID = ""; // TODO: Generate and set GUID
    }

//    public int generateUID() {
//
//    };

    public String getGUID() {
        return this.GUID;
    };

    public String getGroupName() {
        return this.groupName;
    }

    public List<String> getGroupMembers() {
        return this.groupMembers;
    }

    public List<Expense> getExpenseList() {
        return this.expenseList;
    }

    public String getDescription() {
        return this.description;
    }


}
