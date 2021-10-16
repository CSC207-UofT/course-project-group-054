package use_cases;
import entities.*;
import data.*;
public class ExpenseManager {
    public static void create_temp(){

    }

    public static StringBuilder show_group(Person p){
        StringBuilder lst = new StringBuilder("List of groups:\n");
        int counter = 0;
        for (Group g: Data.groups) {
            if (g.getGroupMembers().contains(p.getEmail())) {
                lst.append(g);
                lst.append("\n");
                counter++;
            } //
        }

        if (counter > 0) {
            return lst;
        }

        return new StringBuilder("You don't have any groups now.");
    }

//    /**
//     * The payBill method pays the bill of the expense specified and updates payee and expense.
//     *
//     * Preconditions:
//     * - expense in group.expenseList
//     * - amount <= expense.cost
//     *
//     * @param group - The group where the expense is housed.
//     * @param payee - The person that pays the bill.
//     * @param amountPaid - The amount the person paid.
//     * @param expensePaid - The name of the expense that was paid for.
//     */
//    public void payBill(Group group, Person payee, double amountPaid,
//                        String expensePaid, boolean split) {
//        // If we are not splitting the bill update the pay the payee's debt
//        if (!split) {
//            payDebt(group, payee, amountPaid, expensePaid);
//        }
//        // Otherwise update the balances of the group members.
//        else{
//            splitBill(group, payee, amountPaid, expensePaid);
//        }
//    }
//
//    /**
//     * payBill's helper; used when the user wants to pay the whole bill.
//     * @param group - The group where the expense is housed.
//     * @param payee - The person that pays the bill.
//     * @param amountPaid - The amount the person paid.
//     * @param expensePaid - The name of the expense that was paid for.
//     */
//    public void splitBill(Group group, Person payee, double amountPaid,
//                          String expensePaid){
//        // Decrease the balance of each person by the cost over the total number of people.
//        double payeeAmount = payee.getBalance() - (amountPaid/group.getGroupMembers().size());
//        for(Person person: group.getGroupMembers()){
//            payee.setBalance(payeeAmount);
//            payee.setAmountsOwed(group, payee.getAmountsOwed().get(group) - amountPaid);
//        }
//        // Edit Group Expense Cost
//        for (Expense expense: group.getExpenseList()){
//            if (expense.getTitle().equals(expensePaid)){
//                expense.setAmount(expense.getAmount() - amountPaid);
//            }
//        }
//        payee.setBalance(payee.getBalance());
//    }
//
//    /**
//     * payBill's helper; used when the user wants to pay off the debt of the group.
//     * @param group - The group where the expense is housed.
//     * @param payee - The person that pays the bill.
//     * @param amountPaid - The amount the person paid.
//     * @param expensePaid - The name of the expense that was paid for.
//     */
//    public void payDebt(Group group, Person payee, double amountPaid,
//                        String expensePaid){
//        // Get and set the balance of the payee.
//        double payeeBal = payee.getBalance();
//        payee.setBalance(payeeBal - amountPaid);
//        payee.setAmountsOwed(group, payee.getAmountsOwed().get(group) - amountPaid);
//
//        // Update the specified expense.
//        for (Expense expense: group.getExpenseList()){
//            if (expense.getTitle().equals(expensePaid)){
//                expense.setAmount(expense.getAmount() - amountPaid);
//                return;
//            }
//        }
//    }


}
