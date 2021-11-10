package controller;

import entities.Group;

import java.util.List;

public interface InOut {
    String getInput();

    void sendOutput(Object s);

    void menuView();

    void loginView();

    void signUpView();

    int getActionView(String[] actions);

    Group createGroupView();

    Controller.ExpenseType getExpenseType();

    String getExpenseTitleView();

    double getExpenseAmountView();

    String getGroupNameView(StringBuilder currentGroups);

    void outputGroupExpenseCreationSuccess();

    void outputGroupExpenseCreationFailure();

    List<String> getPeopleNonGroupExpenseView();

    void outputNonGroupExpenseCreationSuccess(List<String> expenses, String userExpense, List<String> people);

    void outputExpenseExceptionResult();

    String getBudgetNameView();

    double getBudgetMaxSpendView();

    void outputBudgetCreationSuccess();

    void outputBudgetCreationFailure();
}