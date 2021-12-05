package com.example.compound.cli_controllers;

public interface InOut {
    String getInput();

    void sendOutput(Object s);

    String requestInput(String attribute);

    int getActionView(String[] actions);
}