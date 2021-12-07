package com.example.compound.entities;

import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

import java.util.HashMap;

public class ExpenseTest {
    Expense e;
    Person p;
    User u;

    @Before
    public void TestSetUp() {
        p = new Person("name", 100.01, "email");
        u = new User("name", 100.01, "email", "password");

        HashMap<Person, Double> whoPaid = new HashMap<>();
        whoPaid.put(p, 20.01);
        HashMap<Person, Double> whoBorrowed = new HashMap<>();
        whoPaid.put(u, 10.05);

        e = new Expense("0", "title", 20.01, whoPaid, whoBorrowed);
    }

    @Test
    public void TestGetAmount() {
        Assertions.assertEquals(120.01, e.getAmount(), 0);
    }

    @Test
    public void TestEUID() {
        Assertions.assertEquals("1", e.getEUID());
    }

    @Test
    public void TestSettleExpenseBorrowed() {
        e.settleExpenseBorrowed(u, 10.05);
        Assertions.assertEquals(10.01, e.getAmount(), 0);
    }

    @Test
    public void TestSettleExpenseLent() {
        e.settleExpenseLent(u, 10.05);
        Assertions.assertEquals(10.01, e.getAmount(), 0);
    }
}
