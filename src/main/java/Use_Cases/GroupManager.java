package Use_Cases;
import Data.Data;
import Entities.*;
import java.util.*;
public class GroupManager {
    public static void showGroup(User u){
        ArrayList<Group> lst  = new ArrayList();
        int counter = 0;
        for (Group g: Data.GROUPS){
            if (g.getGroupMembers().contains(u)){
                lst.add(g);
                counter += 1;
            }
        }
        if (counter > 0){
            for (int i = 0; i < counter; i++){
                System.out.println(lst.get(i));
            }
        }
        System.out.println("You don't have any groups");
    }
}
