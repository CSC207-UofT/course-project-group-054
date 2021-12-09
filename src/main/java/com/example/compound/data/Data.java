package com.example.compound.data;

import com.example.compound.entities.*;
import com.example.compound.use_cases.gateways.RepositoryGateway;
import org.springframework.stereotype.Repository;

import java.util.*;

/*
The class representing the data.
 */
@Repository
public class Data implements RepositoryGateway {
    private final List<User> users = new ArrayList<>();
    private final List<Person> persons = new ArrayList<>();
    private final List<Expense> expenses = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();
    private final List<Budget> budgets = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    private int groupCounter = 0;
    private int budgetCounter = 0;
    private int itemCounter = 0;

    public void initializeData() {
        // Creating dummy users
        users.add(new User(0, "Rohan", "rohan.tinna@mail.utoronto.ca",
        "rohan.tinna@mail.utoronto.ca", 100.0, "password"));
        users.add(new User(1, "Johny", "johny@example.com", "johny@example.com",
                100.0, "password2"));

        // Creating dummy groups
        addGroup(
                new Group("One Direction", new ArrayList<>() {
                    {
                        add("rohan.tinna@mail.utoronto.ca");
                        add("johny@example.com");
                    }
                }, new ArrayList<>(), "")
        ); // Group with 2 users

        addGroup(
                new Group("Avengers", new ArrayList<>() {
                    {
                        add("rohan.tinna@mail.utoronto.ca");
                    }
                }, new ArrayList<>(), "")
        ); // Group with 1 user

        addGroup(
                new Group("Impossible Group", new ArrayList<>(), new ArrayList<>(), "")
        ); // Empty group
    }

    public List<String> getExpenseStrings() {
        List<String> expenseStrings = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseStrings.add(expense.toString());
        }
        return expenseStrings;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        String GUID = Integer.toString(this.groupCounter);
        group.setGUID(GUID);
        this.groupCounter++;
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public String addBudget(Budget budget) {
        this.budgets.add(budget);
        String BUID = Integer.toString(this.budgetCounter);
        budget.setBUID(BUID);
        this.budgetCounter++;
        System.out.println(budget.getName());
        return BUID;
    }

    public String addItem(Item item) {
        this.items.add(item);
        String IUID = Integer.toString(this.itemCounter);
        item.setIUID(IUID);
        this.itemCounter++;
        return IUID;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public List<Expense> getExpenses() {
        return this.expenses;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Budget> getBudgets() {
        return this.budgets;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public Budget findByBUID(String BUID) {
        for (Budget b : budgets) {
            if (b.getBUID().equals(BUID)) {
                return b;
            }
        }
        return null;
    }

    public Group findByGUID(String GUID) {
        for (Group g : groups) {
            if (g.getGUID().equals(GUID)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public void removeGroup(Group group) {
        this.groups.remove(group);
    }

    @Override
    public void removeExpense(Expense expense) {
        this.expenses.remove(expense);
    }

    @Override
    public void removeUser(User user) {
        this.users.remove(user);
    }

    @Override
    public void removeBudget(Budget budget) {
        this.budgets.remove(budget);
    }

    @Override
    public void removeBudget(String BUID) {
        budgets.removeIf(budget -> budget.getBUID().equals(BUID));
    }

    @Override
    public void updateGroup(Group group) {
        for (Group g : groups) {
            if (g.getGUID().equals(group.getGUID())) {
                groups.remove(g);
                groups.add(group);
                return;
            }
        }
    }

    @Override
    public void updateBudget(Budget budget) {
        for (Budget b : budgets) {
            if (b.getBUID().equals(budget.getBUID())) {
                budgets.remove(b);
                budgets.add(b);
                return;
            }
        }
    }

    @Override
    public void updateItem(Item item) {
        for (Item i : items) {
            if (i.getIUID().equals(item.getIUID())) {
                items.remove(i);
                items.add(item);
                return;
            }
        }
    }

    @Override
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public int getNewGUID() {
        return this.groupCounter;
    }

    public int getNewEUID() {
        return expenses.size();
    }

    public int getNewUUID() {
        return users.size();
    }

    public int getNewBUID() {
        return this.budgetCounter;
    }

    public int getNewIUID() {
        return this.itemCounter;
    }

    @Override
    public void addPerson(Person person) {
        this.persons.add(person);
    }

    @Override
    public User findByUUID(String UUID) {
        for (User u : users) {
            if (String.valueOf(u.getUUID()).equals(UUID)) {
                return u;
            }
        }
        return null;
    }
}
