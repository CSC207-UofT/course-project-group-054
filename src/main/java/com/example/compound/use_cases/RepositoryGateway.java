package com.example.compound.use_cases;

import com.example.compound.entities.*;
import java.util.List;

public interface RepositoryGateway {
    void addGroup(Group group);

    void addExpense(Expense expense);

    void addPerson(Person person);

    List<Group> getGroups();

    List<Expense> getExpenses();

    List<Person> getPersons();
}
