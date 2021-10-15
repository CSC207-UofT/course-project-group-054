public class User extends Person {
    private String uid;
    private String username; // Should username be a private or public attribute?

    /**
     * Constructor for User class.
     * @param name: Full name of the user.
     * @param email: Email address of the user.
     */
    public User(String name, String email) {
        super(name, email);
        this.username = email;
        this.uid = "";
    }

    /**
     * Method to edit/update user profile.
     * @return True if profile successfully updated, false otherwise.
     */
    public boolean updateProfile() {
        return false;
    }
}
