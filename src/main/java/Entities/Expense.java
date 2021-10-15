/*
 * Below is the Expense class which represents the origin of our program.
 */
package Entities;


import java.time.*;
import java.util.*;


public class Expense {

    private String title;
    private double cost;
    private LocalDateTime time;
    private List<Person> payers;
    private String description;


    /**
     * Construct Expense, with title, cost, payers, note, and current time
     * @param title the title of the Expense
     * @param cost the cost of the Expense
     * @param payers the list of payers of the Expense
     * @param description the description of the Expense
     */
    public Expense(String title, double cost, List<Person> payers, String description){
        this.title = title;
        this.cost = cost;
        this.time = LocalDateTime.now();
        this.payers = payers;
        this.description = description;
    }

    public String getTitle(){return this.title;}

    public double getCost(){return this.cost;}

    public LocalDateTime getTime(){return this.time;}

    public List<Person> getPayers(){return this.payers;}

    public String getDescription(){return this.description;}



}
