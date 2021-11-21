package com.example.compound.use_cases;

import com.example.compound.data.Data;
import com.example.compound.entities.*;
import com.example.compound.use_cases.ExpenseManager;
import com.example.compound.use_cases.gateways.RepositoryGateway;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    UserManager u;
    ExpenseManager e;
    GroupManager g;
    Data data;
    User user1;
    User user2;
    Expense expense;

    @BeforeEach
    void setUp() {
        data = new Data();
        data.initializeData();
        u = new UserManager(data);
        e = new ExpenseManager(data);
        g = new GroupManager(data);
        user1 = new User("Rohan", 100.0, "rohan.tinna@mail.utoronto.ca", "password");
        user2 = new User("Johny", 100.0, "johny@example.com", "password2");
        Map<Person, Double> m1 = new HashMap<>();
        Map<Person, Double> m2 = new HashMap<>();
        //expense = new Expense("0", "testExpense", 100.0, m1, m2);
        user2.addExpense(Objects.requireNonNull(e.createExpense(
                "testExpense", 100.0, m1, m2, u)));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getExpenses() {
        StringBuilder s1 = u.getExpenses(user1);
        String news1 = s1.toString();
        StringBuilder s2 = u.getExpenses(user2);
        String news2 = s2.toString();
        String newString = "RECENT EXPENSES\n" + "EUID  Title                People\n" +
                "---------------------------------\n" +
                "0     testExpense     0" + "\n";
        String news3 = "You don't have any expenses now.\n";
        assertEquals(news3, news1);
        assertEquals(newString, news2);
    }

    @Test
    void getUser() {
        User user = u.getUser("rohan.tinna@mail.utoronto.ca");
        String actualName = user.getName();
        String expectedName = user1.getName();
        assertEquals(actualName, expectedName);
    }

    @Test
    void createUser() {
        User user = u.createUser("test", 100.0, "test@example.com", "password");
        assertEquals("test", user.getName());
        assert data.users.contains(user);
    }

    @Test
    void getProfile() {
        StringBuilder s1 = u.getProfile(user1, g);
        String out = "Name: Rohan,\n" +
                "Email: rohan.tinna@mail.utoronto.ca,\n" +
                "Balance: 100.0,\n" +
                "Expense(s): \n" + "You don't have any expenses now.\n" +
                "List of groups:\n" +
                "One Direction\n" +
                "Avengers\n";
        assertEquals(out, s1.toString());
    }

    @Test
    void setName() {
        assertEquals("Johny", user2.getName());
        user2.setName("Hello");
        assertEquals("Hello", user2.getName());
    }

    @Test
    void setEmail() {
        assertEquals("johny@example.com", user2.getEmail());
        user2.setName("Hello@example.com");
        assertEquals("Hello@example.com", user2.getName());
    }
}