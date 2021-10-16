package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExtendedGroupTest {

    ExtendedGroup exGroup;
    Person p;
    User u_1;
    User u_2;
    Expense e_1;
    Expense e_2;
    List<Person> lst_p1;

    @BeforeEach
    public void setUp() {
        p = new Person("name", 100.01, "email");
        u_1 = new User("name", 100.01, "email");
        u_1 = new User(200.01, "email_2");

        lst_p1 = new ArrayList<>();
        lst_p1.add(p);
        lst_p1.add(u_1);

        List<Person> lst_p2 = new ArrayList<>();
        lst_p2.add(p);
        lst_p2.add(u_1);
        lst_p2.add(u_2);

        e_1 = new Expense("title", 120.01, lst_p1, "description");
        e_2 = new Expense("title_2", 320.01, lst_p2, "description_2");
        List<Expense> lst_e = new ArrayList<>();
        lst_e.add(e_1);
        lst_e.add(e_2);

        exGroup = new ExtendedGroup("GroupTitle", lst_p1, lst_e, "GroupDescription");
    }

    @Test
    public void getUID() {
        /*
        TODO: Implement this method
         */
        assertEquals(0, exGroup.getUID());
    }

    @Test
    public void getGroupName() { assertEquals("GroupTitle", exGroup.getGroupName());
    }

    @Test
    public void getGroupMembers() {
        assertEquals(p, exGroup.getGroupMembers().get(0));
        assertEquals(u_1, exGroup.getGroupMembers().get(1));
        assertFalse(exGroup.getGroupMembers().contains(u_2));
    }

    @Test
    public void getExpenseList() {
        assertEquals(e_1, exGroup.getExpenseList().get(0));
        assertEquals(e_2, exGroup.getExpenseList().get(1));
    }

    @Test
    public void getDescription() {
        assertEquals("GroupDescription", exGroup.getDescription());
    }


    @Test
    public void addPerson() {
        User u_3 = new User("user_3", 10.01, "email_3");
        assertTrue(exGroup.addPerson(u_3));
        assertTrue(exGroup.getGroupMembers().contains(u_3));
    }

    @Test
    public void removePerson() {
        assertTrue(exGroup.removePerson(u_2));
        assertFalse(exGroup.getGroupMembers().contains(u_2));
    }

    @Test
    public void addExpense() {
        Expense e_3 = new Expense("title_3", 320.01, lst_p1, "description_3");
        assertTrue(exGroup.addExpense(e_3));
        assertTrue(exGroup.getExpenseList().contains(e_3));
    }

    @Test
    public void removeExpense() {
        assertTrue(exGroup.removeExpense(e_1));
        assertFalse(exGroup.getExpenseList().contains(e_1));
    }



}