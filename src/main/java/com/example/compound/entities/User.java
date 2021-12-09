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
     * Construct a new User with the given UID, name, balance, email, and password.
     * @param uuid     the User's unique identifier
     * @param name     the User's name
     * @param email    the User's email used to contact them
     * @param username the User's username
     * @param balance  the User's balance (the amount owed)
     * @param password the User's password
     */
    public User(int uuid, String name, String email, String username, double balance, String password) {
        super(name, balance, email);
        this.UUID = uuid;
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
    }

    /**
     * Return this user's username.
     * @return this user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Return this user's list of expenses.
     * @return this user's list of expenses
     */
    public List<String> getExpenses() {
        return expenses;
    }

    /**
     * Return this user's password.
     * @return this user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set this user's password to the given value.
     * @param password this user's new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return a String representation of this user.
     * @return a String representation of this user
     */
    @Override
    public String toString() {
        return super.getName();
    }

    /**
     * Add an expense to this user's list.
     * @param E the expense to add
     */
    @Override
    public void addExpense(Expense E) {
        this.expenses.add(E.getEUID());
    }

    /**
     * Return this user's unique identifier.
     * @return this user's unique identifier
     */
    public int getUUID() {
        return this.UUID;
    }
}
