package com.example.compound.entities;

import org.junit.*;

import static org.junit.Assert.*;

public class PersonTest {
    Person p;
    Person p_2;

    @Before
    public void TestSetUp() {
        p = new Person("name", 100.01, "email");
        p_2 = new Person("name_2", 100.02, "email_2");
    }

    @Test
    public void TestGetName() {
        assertEquals("name", p.getName());
        assertEquals("name_2", p_2.getName());
    }

    @Test
    public void TestGetBalance() {
        assertEquals(100.01 , p.getBalance(), 0);
    }

    @Test
    public void TestGetEmail() {
        assertEquals("email", p.getEmail());
        assertEquals("email_2", p_2.getEmail());
    }
}
