package use_cases;

import entities.Expense;
import entities.Person;

public interface GroupFeatures {

    boolean addPerson(Person p);

    boolean removePerson(Person p);

    boolean addExpense(Expense e);

    boolean removeExpense(Expense e);
}
