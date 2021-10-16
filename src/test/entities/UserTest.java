package java.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import Entities.*;

public class UserTest {

    User u;
    User u_2;

    @BeforeEach
    public void setUp() {
        u = new User("name", 100.01, "email");
        u_2 = new User(100.01, "email");
    }

    @Test
    public void getName() {
        assertEquals("name", u.getName());
        assertEquals("email", u_2.getName());
    }


    @Test
    public void getUID() {
        /*
        TODO: Implement this test
         */
        assertEquals(0, u.getUUID());
    }

}