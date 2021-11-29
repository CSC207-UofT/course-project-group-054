package com.example.compound.services;

import com.example.compound.entities.*;

import java.util.List;

public interface GroupService {
    Group newGroup();
    boolean editGroup();
    List<String> getExpenses(int num);
    List<String> getUsers();
}
