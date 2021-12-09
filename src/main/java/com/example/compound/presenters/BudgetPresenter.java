/*
 * This file represents the presenter for the budget controller class.
 */
package com.example.compound.presenters;
import com.example.compound.cli_controllers.InOut;

public class BudgetPresenter {
    private final String[] selectionActions = {
            "Select a budget",
            "Create a new budget",
            "Exit"
    };
    private final String[] budgetActions = {
            "Add an item",
            "Change the quantity of an item",
            "Remove an item",
            "Change the spending limit",
            "Convert the items in this budget into expenses and add them to the group",
            "Delete this budget",
            "Exit"
    };

    public String getAuthenticationCheck(boolean bool){
        if (bool){
            return "Authentication Success!";
        }
        else {
            //inOut.sendOutput("Authentication Failed.");
            return "Authentication Failed.";
        }
    }

    public String getBudgetExistence(boolean bool) {
        if (bool) {
            return "This budget does not exist.";
        } else {
            return "This budget exists!";
        }
    }

    public String getValidAmount(boolean bool){
        if (bool) {
            return "Valid Amount.";
        } else {
            return "Invalid Amount.";
        }
    }

    public String noItemAvailable(boolean bool){
        if(bool){
            return "The item was not available.";
        }
        else {
            return "The item was available!";
        }
    }

    public String getFailure(boolean bool){
        if (bool){
            return "Success! The operation was complete";
        }
        else {
            return "Failure, the operation was incomplete.";
        }
    }

    public String requestInt(){
        return "Please enter a valid integer.";
    }

    public String requestQuantity(){
        return "Please enter a valid (Integer) quantity.";
    }

    public String requestName(){
        return "Please enter the name.";
    }

    public String requestAmount(){
        return "Please enter a valid (Double) amount.";
    }

    public String requestCost(){
        return "Please enter a valid (Double) cost.";
    }

    public String[] requestSelectionActions(){
        return this.selectionActions;
    }

    public String[] requestBudgetActions(){
        return this.budgetActions;
    }
}
