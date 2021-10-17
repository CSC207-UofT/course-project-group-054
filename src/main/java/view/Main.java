package view;

import controller.Controller;
import data.Data;

public class Main {
    public static void main(String[] args) {
        Data.initializeData();

        View view = new View();

        do {
            view.menuView();
        } while (Controller.getIsNotLoggedIn());
    }
}
