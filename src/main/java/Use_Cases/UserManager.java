package Use_Cases;

import Entities.*;

public class UserManager {

    public static double show_balance(Person p){
        return p.getBalance();
    }

    public static void updateProfile(Person p) {

    }

    public boolean createExpense() {
        return true;
    }
}
