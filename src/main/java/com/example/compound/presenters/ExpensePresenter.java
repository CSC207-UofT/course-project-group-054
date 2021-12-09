package com.example.compound.presenters;

import java.util.Set;

public class ExpensePresenter {

    public String requestBorrowedOrLent(){
        return "Enter amount borrowed/lent: (0.00)";
    }

    public String requestBorrowedOrLentAmount(){
        return "Enter amount borrowed/lent: (0.00)";
    }

    public String addMore(){
        return "Do you want to add more people to this expense? (y/n)";
    }

    public String error(){
        return "There was an error, please try again.";
    }

    public String requestName(){
        return "Please enter their name";
    }

    public String requestEmail(){
        return "Please enter their email";
    }

    public String borrowedVsLent(Set<String> borrowedSoFar, Set<String> lentSoFar){
        return "borrowed: " + borrowedSoFar + "lent: " + lentSoFar;
    }
}
