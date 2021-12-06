package com.example.compound.use_cases;

import com.example.compound.data.Data;
import com.example.compound.entities.User;
import org.junit.Before;
import org.junit.Test;

public class CurrentUserManagerTest {
    Data d;
    CurrentUserManager cm;
    User u;

    @Before
    public void setUp(){
        d = new Data();
        cm = new CurrentUserManager(d);
        u = new User("name", 100, "email", "password");

        d.addUser(u);
    }

    @Test
    public void testSetCurrentUserUUID(){
        String id = String.valueOf(u.getUUID());
        cm.setCurrentUser(id);
        assert cm.getCurrentUser().equals(u);
    }

    @Test
    public void testSetCurrentUserObj(){
        cm.setCurrentUser(u);
        assert cm.getCurrentUser().equals(u);
    }

    @Test
    public void testResetCurrentUser(){
        cm.resetCurrentUser();
        assert cm.getCurrentUser() == null;
    }
}
