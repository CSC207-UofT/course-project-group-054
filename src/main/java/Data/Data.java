package Data;

import Entities.*;
import java.util.*;

public class Data {
    public static List<Person> USERS = new ArrayList<>();
    public static List<Expense> EXPENSES = new ArrayList<>();
    public static List<Group> GROUPS = new ArrayList<>();

    public static void initializeData() {
        // Creating dummy users
        USERS.add(new User("Rohan", 100, "rohan.tinna@mail.utoronto.ca"));
        USERS.add(new User("Johny", 100, "johny@example.com"));

        // Creating dummy expenses


        // Creating dummy groups
        GROUPS.add(
                new Group("One Direction", new ArrayList<String>() {
                    {
                        add("rohan.tinna@mail.utoronto.ca");
                        add("johny@example.com");
                    }
                }, new ArrayList<Expense>(), "")
        ); // Group with 2 users

        GROUPS.add(
                new Group("Avengers", new ArrayList<String>() {
                    {
                        add("rohan.tinna@mail.utoronto.ca");
                    }
                }, new ArrayList<Expense>(), "")
        ); // Group with 1 user

        GROUPS.add(
                new Group("Impossible Group", new ArrayList<String>(), new ArrayList<Expense>(), "")
        ); // Empty group
    }


}
