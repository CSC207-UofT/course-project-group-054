package com.example.compound.cli_controllers;

import com.example.compound.entities.Person;
import com.example.compound.presenters.ExpensePresenter;
import com.example.compound.use_cases.CurrentUserManager;
import com.example.compound.use_cases.ExpenseManager;
import com.example.compound.use_cases.PersonManager;
import com.example.compound.use_cases.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpenseController {
    private final CurrentUserManager currentUserManager;
    private final UserManager userManager;
    private final PersonManager personManager;
    private final ExpenseManager expenseManager;
    private final ExpensePresenter expensePresenter;

    public ExpenseController(CurrentUserManager currentUserManager, UserManager userManager,
                             PersonManager personManager, ExpenseManager expenseManager){
        this.currentUserManager = currentUserManager;
        this.userManager = userManager;
        this.personManager = personManager;
        this.expenseManager = expenseManager;
        this.expensePresenter = new ExpensePresenter();
    }
    /**
     * Create the view where we interact with the functions of Expense.
     * @param inOut the user interface object
     * @param expenseTitle The title of the expense
     */
    public void createExpenseView(InOut inOut, String expenseTitle) {
        HashMap<Person, Double> borrowedSoFar = new HashMap<>();
        HashMap<Person, Double> lentSoFar = new HashMap<>();

        List<Person> people = new ArrayList<>();
        people.add(currentUserManager.getCurrentUser());

        inOut.sendOutput(expensePresenter.requestBorrowedOrLentAmount());
        double amount = Float.parseFloat(inOut.getInput());

        inOut.sendOutput(expensePresenter.requestBorrowedOrLent());
        boolean userBorrow = inOut.getInput().equals("b");
        if (userBorrow){
            currentUserManager.getCurrentUser().updateBalance(amount);
            borrowedSoFar.put(currentUserManager.getCurrentUser(), amount);
        }
        else{
            currentUserManager.getCurrentUser().updateBalance(-amount);
            lentSoFar.put(currentUserManager.getCurrentUser(), amount);
        }

        boolean addMorePeople = Boolean.TRUE;
        do {
            inOut.sendOutput(expensePresenter.addMore());
            String input2 = inOut.getInput();
            switch (input2) {
                case "y" -> caseYHelper(inOut, borrowedSoFar, lentSoFar);
                case "n" -> {
                    if (people.size() == 0) {
                        inOut.sendOutput(expensePresenter.error());
                    } else {
                        addMorePeople = Boolean.FALSE;
                    }
                }
            }
        } while (addMorePeople);
        currentUserManager.getCurrentUser().addExpense(
                Objects.requireNonNull(
                        expenseManager.createExpense(
                                expenseTitle, amount, lentSoFar, borrowedSoFar, userManager)));
        System.out.println("borrowed: " + borrowedSoFar.keySet()
                + "lent: " + lentSoFar.keySet());
    }

    /**
     * A helper method for case Y in the above createExpenseView.
     * @param inOut the user interface object
     * @param borrowedSoFar A map that stores people that borrowed so far
     * @param lentSoFar A map that stores people that lent so far
     */
    private void caseYHelper(InOut inOut, HashMap<Person, Double> borrowedSoFar, HashMap<Person, Double> lentSoFar) {
        inOut.sendOutput(expensePresenter.requestName());
        String name = inOut.getInput();

        inOut.sendOutput(expensePresenter.requestEmail());
        String email = inOut.getInput();

        inOut.sendOutput(expensePresenter.requestBorrowedOrLent());
        String borrowOrLend = inOut.getInput();

        inOut.sendOutput(expensePresenter.requestBorrowedOrLentAmount());
        String amountUsedStr = inOut.getInput();
        double amountUsed = Double.parseDouble(amountUsedStr);

        boolean borrowed = borrowOrLend.equals("b");

        // If we find the user in the database then update bal
        if (userManager.getUserEmail(email) != null) {
            currentUserManager.setCurrentUser(userManager.getUserEmail(email));

            if (borrowed){
                borrowedSoFar.put(currentUserManager.getCurrentUser(), amountUsed);
            }
            else {
                lentSoFar.put(currentUserManager.getCurrentUser(), amountUsed);
            }
            currentUserManager.getCurrentUser().updateBalance(amountUsed);
        }
        // Otherwise, create a stand in person.
        else {
            if (borrowed){
                borrowedSoFar.put(personManager.createPerson(name, 0.0, ""), amountUsed);
            }
            else {
                lentSoFar.put(personManager.createPerson(name, 0.0, ""), amountUsed);
            }
        }
    }


}
