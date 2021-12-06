package com.example.compound.services;

import com.example.compound.entities.Group;
//import com.example.compound.repositories.GroupRepository;
import com.example.compound.use_cases.gateways.GroupRepositoryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

//    @Qualifier("groupRepositoryImpl")
//    @Autowired
//    GroupRepository repository;

    @Override
    public int newGroup(String name, String description, List<String> members) {
        try {
//            return repository.addGroupToDatabase(name, description, members);
//            repository.save(new Group());
            return 1;
        } catch (Exception ignored) {}
        return 0;
    }

    @Override
    public boolean editGroup() {
        return false;
    }

    @Override
    public List<String> getExpenses(int num) {
        return null;
    }

    @Override
    public List<String> getUsers() {
        return null;
    }
}
