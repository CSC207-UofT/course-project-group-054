package entities;

import java.util.List;

public class ExtendedGroup extends Group{
    private int UID;
    /**
     * Construct a group with groupName, groupMembers, expenseList, and description.
     *
     * @param groupName    the name of the group
     * @param groupMembers the members in the group
     * @param expenseList  the list of expenses in the group
     * @param description  the description of the group
     */
    public ExtendedGroup(String groupName, List<Person> groupMembers, List<Expense> expenseList, String description) {
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


    /**
     * Add a person to the groupMembers
     * @param p the person to be added
     * @return true iff the person is added successfully
     */
    @Override
    public boolean addPerson(Person p) {
        if(!this.groupMembers.contains(p)){
            this.groupMembers.add(p);
            return true;
        }
        return false;
    }

    /**
     * Remove a person from the groupMembers
     * @param p the person to be removed
     * @return true iff the person is removed successfully
     */
    @Override
    public boolean removePerson(Person p) {
        if(!this.groupMembers.contains(p)){
            this.groupMembers.remove(p);
            return true;
        }
        return false;
    }

    /**
     * Add an expense to the expenseList
     * @param e the expense to be added
     * @return true iff the expense is added successfully
     */
    @Override
    public boolean addExpense(Expense e) {
        if(!this.expenseList.contains(e)){
            this.expenseList.add(e);
            return true;
        }
        return false;
    }

    /**
     * Remove an expense from the expenseList
     * @param e the expense to be removed
     * @return true iff the expense is removed successfully
     */
    @Override
    public boolean removeExpense(Expense e) {
        if(this.expenseList.contains(e)){
            this.expenseList.remove(e);
            return true;
        }
        return false;
    }

}
