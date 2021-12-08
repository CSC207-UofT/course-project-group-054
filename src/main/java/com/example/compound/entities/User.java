package com.example.compound.entities;

//import org.hibernate.annotations.Table;
//import org.hibernate.annotations.Type;
//import org.hibernate.annotations.TypeDef;

import java.util.*;

/**
 * A subclass of Person named User.
 * This class is identical to Person, except that it has a UID and a list of expenses.
 */
//@Entity
public class User extends Person implements AccountFeatures {
    private final int UUID;
    private final String username;
    private final List<String> expenses;
    private String password;

    /**
     * Construct a new User with the given name, balance, email, and password.
     * @param name     the User's name
     * @param balance  the User's balance (the amount owed)
     * @param email    the User's email used to contact them
     * @param password the User's password
     */

    public User(int uuid, String name, String email, String username, double balance, String password) {
        super(name, balance, email);
        this.UUID = uuid;
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
    }

    public String getPassword() {
        return this.password;
    }
    public String getUsername() {
        return username;
    }

    public List<String> getExpenses() {
        return expenses;
    }
    

    public void setPassword(String password) {
        this.password = password;
    }

    //TODO: Create a proper UUID Algorithm, perhaps in the repository instead of here
    /*
    Generate a Unique User ID.
     */
    public int generateUUID() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public void addExpense(Expense E) {
        this.expenses.add(E.getEUID());
    }

    public int getUUID() {
        return this.UUID;
    }
}
