package com.example.compound.controller;

public interface InOut {
    String getInput();

    void sendOutput(Object s);

    String requestInput(String attribute);

    int getActionView(String[] actions);
}