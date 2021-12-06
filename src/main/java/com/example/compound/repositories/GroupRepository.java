package com.example.compound.repositories;

import com.example.compound.entities.Group;
import com.example.compound.use_cases.gateways.RepositoryGatewayI;

import java.util.List;

public class GroupRepository implements RepositoryGatewayI<Group> {
    @Override
    public Group findByUID(String UID) {
        return null;
    }

    @Override
    public List<Group> findAll() {
        return null;
    }

    @Override
    public String save(Group group) {
        return null;
    }

    @Override
    public boolean update(Group group) {
        return false;
    }

    @Override
    public boolean deleteById(String UID) {
        return false;
    }
}
