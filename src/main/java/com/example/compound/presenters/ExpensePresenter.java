package com.example.compound.presenters;

public class ExpensePresenter {

    /**
     * Return a String representing whether money was borrowed or lent.
     * @return a String representing whether money was borrowed or lent
     */
    public String requestBorrowedOrLent(){
        return "Enter amount borrowed/lent: (0.00)";
    }

    /**
     * Return a String representing the amount borrowed or lent.
     * @return a String representing the amount borrowed or lent
     */
    public String requestBorrowedOrLentAmount(){
        return "Enter amount borrowed/lent: (0.00)";
    }

    /**
     * Return a String representing whether to add more people.
     * @return a String representing whether to add more people
     */
    public String addMore(){
        return "Do you want to add more people to this expense? (y/n)";
    }

    /**
     * Return a String showing an error.
     * @return a String showing an error
     */
    public String error(){
        return "There was an error, please try again.";
    }

    /**
     * Return a String requesting a name.
     * @return a String requesting a name
     */
    public String requestName(){
        return "Please enter their name";
    }

    /**
     * Return a String requesting an email.
     * @return a String requesting an email
     */
    public String requestEmail(){
        return "Please enter their email";
    }
}
