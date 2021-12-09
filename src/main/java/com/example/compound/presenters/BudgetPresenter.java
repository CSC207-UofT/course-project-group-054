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

    /**
     * Return a String outputting a success or failure for authentication.
     * @param bool whether authentication was successful
     * @return a String outputting whether authentication succeeded
     */
    public String getAuthenticationCheck(boolean bool){
        if (bool){
            return "Authentication Success!";
        }
        else {
            return "Authentication Failed.";
        }
    }

    /**
     * Return a String outputting a success or failure for getting a budget.
     * @param bool whether the budget was obtained
     * @return a String outputting whether the budget was obtained
     */
    public String getBudgetExistence(boolean bool) {
        if (bool) {
            return "This budget exists";
        } else {
            return "This budget does not exist";
        }
    }

    /**
     * Return a String outputting that there are no budgets.
     * @return a String outputting that there are no budgets
     */
    public String getNoBudgets() {
        return "There are no budgets";
    }

    /**
     * Return a String outputting whether input was valid
     * @param bool whether input was valid
     * @return a String outputting whether input was valid
     */
    public String getValidAmount(boolean bool){
        if (bool) {
            return "Valid Amount.";
        } else {
            return "Invalid Amount.";
        }
    }

    /**
     * Return a String outputting whether the item was available
     * @param bool whether the item was available
     * @return a String outputting whether the item was available
     */
    public String noItemAvailable(boolean bool){
        if(bool){
            return "The item was not available.";
        }
        else {
            return "The item was available!";
        }
    }

    /**
     * Return a String outputting whether the operation was complete
     * @param bool whether the operation was complete
     * @return a String outputting whether the operation was complete
     */
    public String getResult(boolean bool){
        if (bool) {
            return "Success! The operation was complete";
        }
        else {
            return "Failure, the operation was incomplete.";
        }
    }

    /**
     * Return a String requesting an integer.
     * @return a String requesting an integer
     */
    public String requestInt(){
        return "a valid integer";
    }

    /**
     * Return a String requesting a quantity.
     * @return a String requesting a quantity
     */
    public String requestQuantity(){
        return "a valid (Integer) quantity";
    }

    /**
     * Return a String requesting a name.
     * @return a String requesting a name
     */
    public String requestName(){
        return "the name";
    }

    /**
     * Return a String requesting an amount.
     * @return a String requesting an amount
     */
    public String requestAmount(){
        return "a valid (Double) amount";
    }

    /**
     * Return a String requesting a cost.
     * @return a String requesting a cost
     */
    public String requestCost(){
        return "a valid (Double) cost";
    }

    /**
     * Return a String requesting an action among selectionActions.
     * @return a String requesting an action among selectionActions
     */
    public String[] requestSelectionActions(){
        return this.selectionActions;
    }

    /**
     * Return a String requesting an action among budgetActions.
     * @return a String requesting an action among budgetActions
     */
    public String[] requestBudgetActions(){
        return this.budgetActions;
    }
}
