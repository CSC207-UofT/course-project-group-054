package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import entities.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {

    Expense e;
    Person p;
    User u;

    @BeforeEach
    public void setUp() {
        p = new Person("name", 100.01, "email");
        u = new User("name", 100.01, "email");
        List<Person> lst = new ArrayList<>();
        lst.add(p);
        lst.add(u);
        // TODO: Rewrite this
//        e = new Expense("title", 120.01, lst, "description");
    }

    @Test
    public void getTitle() {
        assertEquals("title", e.getTitle());
    }

    @Test
    public void getCost() {
        assertEquals(120.01, e.getAmount());
    }

    @Test
    public void getPayers() {
//        assertEquals(p, e.getPayer().get(0));
//        assertEquals(u, e.getPayer().get(1));
        // TODO: Rewrite this test
    }

    @Test
    public void getDescription() {
        assertEquals("description", e.getDescription());
    }
}