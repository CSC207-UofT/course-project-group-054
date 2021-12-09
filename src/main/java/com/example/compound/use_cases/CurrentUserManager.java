/* This file represents the UseCase CurrentUserManager, which interacts with an instance
  of user. This was created with the intention of removing Controller's dependency on User.
 */

package com.example.compound.use_cases;
import com.example.compound.entities.User;
import com.example.compound.use_cases.gateways.RepositoryGateway;

/**
 * A use case that stores the User currently being used by the program.
 */
public class CurrentUserManager {
    private User currentUser;
    private final RepositoryGateway repositoryGateway;

    /**
     * Construct a new CurrentUserManager with the given parameters.
     * @param repositoryGateway the repository for all objects
     */
    public CurrentUserManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }

    /**
     * Return the UID of the current User
     * @return the UID of the current User.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the current user to the user specified by the UUID.
     * @param UUID the UUID of the current user.
     */
    public void setCurrentUser(String UUID) {
        this.currentUser = this.repositoryGateway.findByUUID(UUID);
    }

    /**
     * Set the current user to the user specified.
     * This is an overloaded function.
     * @param u the specified user.
     */
    public void setCurrentUser(User u){
        String id = String.valueOf(u.getUUID());
        this.currentUser = this.repositoryGateway.findByUUID(id);
    }

    /**
     * Set the current user to null.
     */
    public void resetCurrentUser(){
        this.currentUser = null;
    }
}
