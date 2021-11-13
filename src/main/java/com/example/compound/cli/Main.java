package com.example.compound.cli;

import com.example.compound.controller.Controller;
import com.example.compound.data.*;

public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        data.initializeData();

        View view = new View();
        Controller controller = new Controller(data);

        do {
            controller.menu(view);

        } while (Controller.getIsNotLoggedIn());
    }
}
