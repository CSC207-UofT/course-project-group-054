/* A subclass of Person named User.
 * This class is identical to Person, except that it has a UID
 * and TODO: Insert functions
 */

package Entities;

public class User extends Person implements AccountFeatures{
    private int UID;

    public User(String name, float balance, String email){
        super(name, balance, email);
        this.UID = generateUID();
    }

    /**
     * Generate a unique integer for this user.
     * @return the integer representing the UID
     */
    @Override
    public int generateUID() {
        /*
        TODO: Implement this method
         */
        return 0;
    }
}
