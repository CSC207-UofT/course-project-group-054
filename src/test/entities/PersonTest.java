package Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    Person p;
    Person p_2;

    @BeforeEach
    public void setUp() {
        p = new Person("name", 100.01, "email");
        p_2 = new Person(100.02, "email");
    }

    @Test
    public void getName() {
        assertEquals("name", p.getName());
        assertEquals("email", p_2.getName());
    }

    @Test
    public void setName() {
        p.setName("test_name");
        assertEquals("test_name", p.getName());
    }

    @Test
    void getBalance() {
        assertEquals(100.01 , p.getBalance());
    }

    @Test
    void setBalance() {
        p.setBalance(200.02);
        assertEquals(200.02, p.getBalance());
    }

    @Test
    void getEmail() {
        assertEquals("email", p.getEmail());
        assertEquals("email", p_2.getEmail());
    }

    @Test
    void setEmail() {
        p.setEmail("test_email");
        assertEquals("test_email", p.getEmail());
    }
}