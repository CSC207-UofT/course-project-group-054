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
                new Group("One Direction", new ArrayList<String>(), new ArrayList<Expense>(), "")
        );
    }


}
