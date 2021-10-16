package Use_Cases;
import Entities.*;
import java.util.*;
import Data.*;
public class GroupManager {
    public static void create_temp(){

    }

    public static StringBuilder showGroup(User u){
        StringBuilder lst  = new StringBuilder("List of groups:\n");
        int counter = 0;
        for (Group g: Data.GROUPS){
            if (g.getGroupMembers().contains(u)){
                lst.append(g);
                lst.append("\n");
                counter += 1;
            }
        }
        if (counter > 0){
            return lst;
        }
        return new StringBuilder("You don't have any groups now.");
    }
}
