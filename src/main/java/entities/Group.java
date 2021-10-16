/*
 * Below is the abstract Group class which represents the origin of our program.
 */
package entities;

import java.util.*;



public abstract class Group {
    protected String groupName;
    protected List<Person> groupMembers;
    protected List<Expense> expenseList;
    protected String description;

    /**
     *Construct a group with groupName, groupMembers, expenseList, and description.
     * @param groupName the name of the group
     * @param groupMembers the members in the group
     * @param expenseList the list of expenses in the group
     * @param description the description of the group
     */
    public Group(String groupName, List<Person> groupMembers, List<Expense> expenseList, String description){
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.expenseList = expenseList;
        this.description = description;
    }

    public abstract int generateUID();


}
