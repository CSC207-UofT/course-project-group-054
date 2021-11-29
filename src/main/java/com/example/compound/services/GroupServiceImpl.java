package com.example.compound.services;

import com.example.compound.entities.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Override
    public Group newGroup() {
        return null;
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
