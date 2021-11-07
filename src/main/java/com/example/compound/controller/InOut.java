package com.example.compound.controller;


import com.example.compound.entities.Group;

public interface InOut {
    String getInput();

    void sendOutput(Object s);

    void menuView();

    void loginView();

    void signUpView();

    int getActionView(String[] actions);

    Group createGroupView();
}