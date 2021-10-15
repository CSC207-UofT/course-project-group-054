package Use_Cases;

import Entities.Expense;
import Entities.Person;

public interface GroupFeatures {

    boolean addPerson(Person p);

    boolean removePerson(Person p);

    boolean addExpense(Expense e);

    boolean removeExpense(Expense e);
}
