package com.example.compound.use_cases;

import com.example.compound.entities.Person;
import com.example.compound.use_cases.gateways.RepositoryGateway;

public class PersonManager {
    private final RepositoryGateway repositoryGateway;

    public PersonManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }

    public Person createPerson(String name, double balance, String email) {
        Person person = new Person(name, balance, email);
        this.repositoryGateway.addPerson(person);
        return person;
    }
}
