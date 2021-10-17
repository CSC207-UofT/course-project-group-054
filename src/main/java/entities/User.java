package entities;

import java.util.*;

/**
 * A subclass of Person named User.
 * This class is identical to Person, except that it has a UID and a list of expenses.
 */
public class User extends Person implements AccountFeatures {

    private static final int UUID = 0;
    public List<String> expenses;

    /**
     * Construct User, giving them the given name, balance, and email.
     *
     * @param name    the User's name
     * @param balance the User's balance (the amount owed)
     * @param email   the User's email used to contact them
     */
    public User(String name, double balance, String email) {
        super(name, balance, email);
        this.expenses = new ArrayList<>();
    }

    public int getUUID() {
        return UUID;
    }
    
    @Override
    public String generateUUID() {
        return null;
    }
}
