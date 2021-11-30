package com.example.compound.entities;

import org.junit.*;

import static org.junit.Assert.*;

public class UserTest {
    User u;
    User u_2;

    @Before
    public void TestSetUp() {
        u = new User("name", 100.01, "email", "password");
        u_2 = new User("name_2", 100.02, "email_2", "password_2");
    }

    @Test
    public void TestGetName() {
        assertEquals("name", u.getName());
        assertEquals("name_2", u_2.getName());
    }
}
