package com.example.compound.entities;

import org.junit.*;

import static org.junit.Assert.*;

public class UserTest {
    User u;
    User u_2;

    @Before
    public void TestSetUp() {
        u = new User(0, "name", "email","email", 100.0, "password");
        u_2 = new User(0, "name_2", "email_2", "email_2", 100.02, "password");
    }

    @Test
    public void TestGetName() {
        assertEquals("name", u.getName());
        assertEquals("name_2", u_2.getName());
    }
}
