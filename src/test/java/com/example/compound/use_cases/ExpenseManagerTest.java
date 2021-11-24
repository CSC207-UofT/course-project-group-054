package com.example.compound.use_cases;

import com.example.compound.data.Data;
import com.example.compound.entities.Expense;
import com.example.compound.entities.Item;
import com.example.compound.entities.Person;
import com.example.compound.entities.User;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class ExpenseManagerTest {
    User u;
    User u2;
    Expense e;
    Data d;
    ExpenseManager em;
    UserManager um;
    Item i;

    @Before
    public void setUp(){
        d = new Data();
        i = new Item("0", "title", 10.0, 2);

        u = new User("name", 100.0, "email@email.com", "password");
        u2 = new User("name2", 100.0, "email2@email.com", "password2");
        d.addUser(u);
        d.addUser(u2);

        HashMap<Person, Double> whoPaid = new HashMap<>();
        HashMap<Person, Double> whoBorrowed = new HashMap<>();

        whoPaid.put(u, 10.0);
        whoBorrowed.put(u2, 10.0);

        e = new Expense("0", "expense", 100.0, whoPaid, whoBorrowed);
        d.addExpense(e);

        em = new ExpenseManager(d);
        um = new UserManager(d);
    }

    @Test
    public void testCreateExpenseAmount(){
        HashMap<Person, Double> whoPaid = new HashMap<>();
        HashMap<Person, Double> whoBorrowed = new HashMap<>();

        whoPaid.put(u, 10.0);
        whoBorrowed.put(u2, 10.0);

        Expense testExpense = em.createExpense("expense", 100.0, whoPaid, whoBorrowed, um);
        assert testExpense.getAmount() == e.getAmount();
    }

    @Test
    public void testCreateExpenseHashmaps(){
        HashMap<Person, Double> whoPaid = new HashMap<>();
        HashMap<Person, Double> whoBorrowed = new HashMap<>();

        whoPaid.put(u, 10.0);
        whoBorrowed.put(u2, 10.0);

        Expense testExpense = em.createExpense("expense", 100, whoPaid, whoBorrowed, um);
        assert testExpense.getWhoBorrowed().equals(e.getWhoBorrowed());
        assert testExpense.getWhoPaid().equals(e.getWhoPaid());
    }

    @Test
    public void testCreateExpenseItem(){
        Expense testItem = em.createExpense(i);
        assert testItem != null;
    }

    @Test
    public void testPayDebtBorrowed(){
        em.payDebt(u2, "0", 5.0, true);
        assert u2.getBalance() == 95.0;
    }

    @Test
    public void testPayDebtLend(){
        em.payDebt(u, "0", 5.0, false);
        assert u2.getBalance() == 95.0;
    }
}
