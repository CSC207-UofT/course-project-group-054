package entities;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import entities.*;

public class ExpenseTest {

    Expense e;
    Person p;
    User u;
    List<String> lst = new ArrayList<>();

    @Before
    public void TestSetUp() {
        p = new Person("name", 100.01, "email");
        u = new User("name", 100.01, "email");
        lst.add(p.getName());
        lst.add(u.getName());
        e = new Expense("title", 120.01, lst);
    }


    @Test
    public void TestgetAmount() {
        assertEquals(120.01, e.getAmount(), 0);
    }

    @Test
    public void TestEUID() {assertEquals("1", e.getEUID());}


    @Test
    public void TestSettleExpense(){
        e.settleExpense();
        assertEquals(0, e.getAmount(), 0);
    }

}