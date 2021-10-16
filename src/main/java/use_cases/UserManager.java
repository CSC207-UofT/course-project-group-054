/*
Below is the UserManager class which edits and returns instances of User.
 */
package use_cases;

import entities.*;

public class UserManager {

    public static double showBalance(Person p){
        return p.getBalance();
    }

    public static void updateProfile(Person p) {
        //TODO: Finish this
    }

    /**
     * Output the amounts owed to each group a User belongs to.
     * @param user - The User of which we want the attributes from.
     */
    public static void showAmountsOwed(User user){
        for(Group group: user.getAmountsOwed().keySet()){
            System.out.println("You owe " + group.getGroupName() + " " + user.getAmountsOwed().get(group));
        }
    }
}
