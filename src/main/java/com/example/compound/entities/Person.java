package com.example.compound.entities;

/**
 * A person who does not necessarily have an account. Direct instantiations of this class (not subclasses) do not have
 * an account.
 */
public class Person {
    protected String name;
    protected double balance;
    protected String email;

    /**
     * Construct a new person with the given name, balance, and email.
     * @param name the Person's name
     * @param balance the Person's balance (the amount owed)
     * @param email the Person's email used to contact them
     */
    public Person(String name, double balance, String email) {
        this.name = name;
        this.balance = balance;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
