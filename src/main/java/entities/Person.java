/*
 * Below is the Person class which represents the origin of our program.
 * This will be the superclass of User and will also be a stand in for
 * a person not in a Group.
 */

package entities;

public class Person {
    protected String name;
    protected double balance;
    protected String email;

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
    }

    public Person(double balance, String email){
        this.name = email;
        this.balance = balance;
        this.email = email;
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

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
