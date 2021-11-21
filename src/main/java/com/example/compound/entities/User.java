package com.example.compound.entities;

//import org.hibernate.annotations.Table;
//import org.hibernate.annotations.Type;
//import org.hibernate.annotations.TypeDef;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;

/**
 * A subclass of Person named User.
 * This class is identical to Person, except that it has a UID and a list of expenses.
 */

//@Entity
public class User extends Person implements AccountFeatures {
    private final int UUID; // TODO: Use this
    public final String username; // TODO: Make private, add methods
    public List<String> expenses; // TODO: Make private, add methods
    private String password;

    /**
     * Construct User, giving them the given name, balance, and email.
     * @param name     the User's name
     * @param balance  the User's balance (the amount owed)
     * @param email    the User's email used to contact them
     * @param password the User's password
     */
    public User(String name, double balance, String email, String password) {
        // TODO: Take in a distinct username
        this(0, name, email, email, balance, password); // TODO: Should this constructor with no UUID ever be used?
    }

    public User(int uuid, String name, String email, String username, double balance, String password) {
        super(name, balance, email);
        this.UUID = uuid;
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
    }

    public String getPassword() { // TODO: Add test in UserTest
        return password;
    }

    public void setPassword(String password) { // TODO: Add test in UserTest
        this.password = password;
    }

    @Override
    public String generateUUID() {
        return null; // TODO: Maybe remove this and have a repository generate a UUID
    }

    public String toString() {
        return this.name;
    }

    @Override
    public void addExpense(Expense E) {
        this.expenses.add(E.getEUID());
    }

    // TODO: removeExpense?
}
