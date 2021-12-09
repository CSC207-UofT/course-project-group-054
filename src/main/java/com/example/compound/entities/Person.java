package com.example.compound.entities;

/**
 * A person who does not necessarily have an account. Direct instantiations of this class (not subclasses) do not have
 * an account.
 */
public class Person {
    private String name;
    private double balance;
    private String email;

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

    /**
     * Return the name of this person.
     * @return the name of this person
     */
    public String getName() {
        return name;
    }

    /**
     * Return the balance of this person.
     * @return the balance of this person
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Add the given amount to this person's balance.
     * @param amount the amount by which to update this person's balance
     */
    public void updateBalance(double amount) {
        this.balance += amount;
    }

    /**
     * Return this person's email address.
     * @return this person's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set this person's name to the given value.
     * @param name this person's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set this person's email to the given value.
     * @param email this person's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set this person's balance to the given value.
     * @param balance this person's balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
