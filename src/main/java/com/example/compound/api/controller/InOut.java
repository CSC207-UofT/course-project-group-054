package com.example.compound.api.controller;

public interface InOut {
    String getInput();

    void sendOutput(Object s);

    String requestInput(String attribute);

    int getActionView(String[] actions);
}