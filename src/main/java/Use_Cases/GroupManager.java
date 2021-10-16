package Use_Cases;
import Entities.*;
import java.util.*;
import Data.*;
public class GroupManager {
    public static void create_temp(){

    }

    public static StringBuilder show_group(Person p){
        StringBuilder lst = new StringBuilder("List of groups:\n");
        int counter = 0;
        for (Group g: Data.GROUPS) {
            if (g.getGroupMembers().contains(p.getEmail())) {
                lst.append(g);
                lst.append("\n");
                counter++;
            }
        }

        if (counter > 0) {
            return lst;
        }

        return new StringBuilder("You don't have any groups now.");
    }
}
