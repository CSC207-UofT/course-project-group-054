package Entities;

import java.util.List;

public class TemporaryGroup extends Group{
    private int UID;
    /**
     * Construct a group with groupName, groupMembers, expenseList, and description.
     *
     * @param groupName    the name of the group
     * @param groupMembers the members in the group
     * @param expenseList  the list of expenses in the group
     * @param description  the description of the group
     */
    public TemporaryGroup(String groupName, List<Person> groupMembers, List<Expense> expenseList, String description) {
        super(groupName, groupMembers, expenseList, description);
        this.UID = generateUID();
    }


    @Override
    public int generateUID() {
        /*
        TODO: Implement this method
         */
        return 0;
    }

    @Override
    public int getUID() {
        return this.UID;
    }

}
