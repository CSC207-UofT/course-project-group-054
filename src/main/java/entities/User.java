/* A subclass of Person named User.
 * This class is identical to Person, except that it has a UID
 * and TODO: Insert functions
 */

package entities;

import java.util.*;


public class User extends Person implements AccountFeatures {
    private final String UUID;
    private List<Group> groups;
    public List<String> expenses; // TODO make private

    /**
     * Construct User, giving them the given name, balance, and email.
     * @param name the User's name
     * @param balance the User's balance (the amount owed)
     * @param email the User's email used to contact them
     */
    public User(String name, double balance, String email){
        super(name, balance, email);
        this.UUID = generateUUID();
        this.expenses = new ArrayList<String>();
    }

    public User(double balance, String email){
        super(balance, email);
        this.UUID = generateUUID();
    }

    /**
     * Generate a unique integer for this user.
     * @return the integer representing the UID
     */

    public String generateUUID() {
        /*
        TODO: Implement this method
         */
        return "Update generateUUID() in User class";
    }

    public String getUUID(){
        return this.UUID;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public boolean addGroups(Group g){
        if(!groups.contains(g)) {
            this.groups.add(g);
            return true;
        }
        return false;
    }
}
