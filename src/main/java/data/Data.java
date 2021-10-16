package data;

import entities.*;
import java.util.*;

public class Data {
    public static List<Person> users = new ArrayList<>();
    public static List<Expense> expenses = new ArrayList<>();
    public static List<Group> groups = new ArrayList<>();

    public static void initializeData() {
        // Creating dummy users
        users.add(new User("Rohan", 100, "rohan.tinna@mail.utoronto.ca"));
        users.add(new User("Johny", 100, "johny@example.com"));

        // Creating dummy expenses


        // Creating dummy groups
        groups.add(
                new Group("One Direction", new ArrayList<String>() {
                    {
                        add("rohan.tinna@mail.utoronto.ca");
                        add("johny@example.com");
                    }
                }, new ArrayList<Expense>(), "")
        ); // Group with 2 users

        groups.add(
                new Group("Avengers", new ArrayList<String>() {
                    {
                        add("rohan.tinna@mail.utoronto.ca");
                    }
                }, new ArrayList<Expense>(), "")
        ); // Group with 1 user

        groups.add(
                new Group("Impossible Group", new ArrayList<String>(), new ArrayList<Expense>(), "")
        ); // Empty group
    }


}
