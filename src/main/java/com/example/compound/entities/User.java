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

    private final int UUID;
    public final String username;
    public List<Integer> expenses;
    private String password;

    /**
     * Construct User, giving them the given name, balance, and email.
     * @param name    the User's name
     * @param balance the User's balance (the amount owed)
     * @param email   the User's email used to contact them
     */
    public User(String name, double balance, String email) {
        super(name, balance, email);
        this.UUID = 0;
        this.username = email;
        this.expenses = new ArrayList<>();
    }

    public User(int uuid, String name, String email, String username, double balance, String password) {
        super(name, balance, email);
        this.UUID = uuid;
        this.username = username;
        this.password = password;
    }

    @Override
    public String generateUUID() {
        return null;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public void addExpense(Expense E) {
        this.expenses.add(E.getEUID());
    }
}
