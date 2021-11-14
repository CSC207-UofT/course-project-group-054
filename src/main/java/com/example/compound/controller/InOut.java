package com.example.compound.controller;

public interface InOut {
    String getInput();

    void sendOutput(Object s);

    void welcome(String appName);

    void outputLoginSuccessView(String name);

    void outputLoginFailureView();

    String requestInput(String attribute);

    int getActionView(String[] actions);

    String getGroupNameView(StringBuilder currentGroups);

    void outputCreateGroupAuthenticationFailure();

    String getBudgetNameView();

    double getBudgetMaxSpendView();

    void outputBudgetCreationSuccess();

    void outputBudgetCreationFailure();
}