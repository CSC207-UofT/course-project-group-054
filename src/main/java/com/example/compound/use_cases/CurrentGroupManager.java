package com.example.compound.use_cases;

import com.example.compound.entities.Group;
import com.example.compound.use_cases.gateways.RepositoryGateway;
import org.springframework.stereotype.Service;

/**
 * A use case that stores the Group being currently used by the program.
 */
@Service
public class CurrentGroupManager {
    private Group currentGroup;
    private final RepositoryGateway repositoryGateway;

    /**
     * Construct a new CurrentGroupManager with the given parameters.
     * @param repositoryGateway the repository for all objects
     */
    public CurrentGroupManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }

    /**
     * Return the UID of the Group being currently used by the program.
     * @return the UID of the Group being currently used by the program
     */
    public String getCurrentGroupUID() {
        return currentGroup.getGUID();
    }

    /**
     * Set the UID of the Group being currently used by the program to the given value
     * @param GUID the new UID of the Group that the program is to use
     */
    public void setCurrentGroup(String GUID) {
        this.currentGroup = this.repositoryGateway.findByGUID(GUID);
    }

    /**
     * Return the current group.
     * @return the current group
     */
    public Group getCurrentGroup() {
        return this.currentGroup;
    }
}

