/* A subclass of Person named User.
 * This class is identical to Person, except that it has a UID
 */

package Entities;

import java.util.*;


public class User extends Person implements AccountFeatures{
    private final int UID;
    private List<Group> groups;

    /**
     * Construct User, giving them the given name, balance, and email.
     * @param name the User's name
     * @param balance the User's balance (the amount owed)
     * @param email the User's email used to contact them
     */
    public User(String name, double balance, String email){
        super(name, balance, email);
        this.groups = new ArrayList<>();
        this.UID = generateUID();
    }

    public User(double balance, String email){
        super(balance, email);
        this.groups = new ArrayList<>();
        this.UID = generateUID();
    }

    /**
     * Generate a unique integer for this user.
     * @return the integer representing the UID
     */
    public int generateUID() {
        /*
        TODO: Implement this method
         */
        return 0;
    }

    public String getUID(){
        return String.valueOf(this.UID);
    }

    public List<Group> getGroups(){
        return this.groups;
    }

    public boolean addGroup(Group g){
//        if(!groups.contains(g)) {
//            this.groups.add(g);
//            return true;
//        }
//        return false;
        return this.groups.add(g);
    }

    @Override
    public String generateUUID() {
        return null;
    }
}
