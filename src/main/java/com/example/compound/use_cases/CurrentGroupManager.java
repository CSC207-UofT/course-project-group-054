package com.example.compound.use_cases;

import com.example.compound.entities.Group;
import com.example.compound.use_cases.gateways.RepositoryGateway;

/**
 * A use case that stores the Group being currently used by the program.
 */
public class CurrentGroupManager {
    private Group currentGroup;
    private final RepositoryGateway repositoryGateway;

    public CurrentGroupManager(RepositoryGateway repositoryGateway) {
        this.repositoryGateway = repositoryGateway;
    }
    /**
     * Return the UID of the Group being currently used by the program.
     * @return the UID of the Group being currently used by the program
     */
    public Integer getCurrentGroupUID() {
        // TODO Rohan fix this
//        return currentGroup.getGUID();
        return null;
    }

    /**
     * Set the UID of the Group being currently used by the program to the given value
     * @param GUID the new UID of the Group that the program is to use
     */
    public void setCurrentGroup(Integer GUID) {
        this.currentGroup = this.repositoryGateway.findByGUID(GUID);
    }

    public Group getCurrentGroup(){return this.currentGroup;}
}

