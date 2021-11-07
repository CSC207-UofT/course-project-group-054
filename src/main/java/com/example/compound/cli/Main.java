package com.example.compound.cli;

import com.example.compound.controller.Controller;
import com.example.compound.data.*;

public class Main {
    public static void main(String[] args) {
        Data.initializeData();

        View view = new View();

        do {
            view.menuView();
        } while (Controller.getIsNotLoggedIn());
    }
}
