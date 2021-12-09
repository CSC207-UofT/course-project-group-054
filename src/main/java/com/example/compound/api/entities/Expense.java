package com.example.compound.api.entities;

import javax.persistence.*;
import java.util.Map;

/*
 * Below is the Expense class which represents the origin of our program.
 */
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer EUID;
    private String title;
    private Double amount;
    private Integer payer; // UUID of person who paid
    @ElementCollection
    @MapKeyColumn
    @CollectionTable
    private Map<Integer, Double> people;

    public Expense() {}

    public Integer getEUID() {
        return this.EUID;
    }
    public String getTitle() {
        return title;
    }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Integer getPayer() {
        return payer;
    }
    public Map<Integer, Double> getPeople() { return people; }


    /**
     * Construct Expense, with title, cost, payers, note, and current time
     * @param title the title of the Expense
     * @param amount the cost of the Expense
     * @param payer UUID of person who paid
     * @param people List containing UIDs of people part of the expense
     */
    //TODO: Implement multiple split types for Phase 2
    public Expense(String title, Double amount,
                   Integer payer,
                   Map<Integer, Double> people) {
        this.title = title;
        this.amount = amount;
        this.payer = payer;
        this.people = people;
    }

    @Override
    public String toString() {
        return this.EUID + ": " + this.title;
    }
}
