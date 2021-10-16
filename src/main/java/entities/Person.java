/*
 * Below is the Person class which represents the origin of our program.
 * This will be the superclass of User and will also be a stand in for
 * a person not in a Group.
 */

package entities;

import java.util.HashMap;

public class Person {
    private String name;
    private double balance;
    private String email;
    private HashMap<Group, Double> amountsOwed;

    /**
     * Construct Person, giving them the given name, balance, and email.
     * @param name the Person's name
     * @param balance the Person's balance (the amount owed)
     * @param email the Person's email used to contact them
     */
    public Person(String name, double balance, String email) {
        this.name = name;
        this.balance = balance;
        this.email = email;
        this.amountsOwed = null;
    }

    public Person(double balance, String email){
        this.name = email;
        this.balance = balance;
        this.email = email;
        this.amountsOwed = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<Group, Double> getAmountsOwed() {
        return amountsOwed;
    }

    public void setAmountsOwed(Group group, double owing) {
        HashMap<Group, Double> tempHash = new HashMap<>();
        tempHash.put(group, owing);
        this.amountsOwed = tempHash;
    }
}
