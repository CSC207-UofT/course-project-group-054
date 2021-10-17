package controller;

import entities.Group;

public interface InOut {
    String getInput();

    void sendOutput(Object s);

    void menuView();

    void loginView();

    void signUpView();

    int getActionView(String[] actions);

    Group createGroupView();

    /* For testing the code */
    void outputGroups();
}