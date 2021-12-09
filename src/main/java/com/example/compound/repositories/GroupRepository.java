package com.example.compound.repositories;

import com.example.compound.use_cases.gateways.RepositoryGatewayI;
import com.example.compound.use_cases.transfer_data.GroupTransferData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A repository for storing Group data transfer objects.
 */
@Repository
public class GroupRepository implements RepositoryGatewayI<GroupTransferData> {
    /**
     * Construct a new repository for Group data transfer objects.
     */
    public GroupRepository() {
    }

    /**
     * Return the Group data transfer object with the given UID from the repository.
     * @param UID the UID of the Group data transfer object to be retrieved
     * @return the Group data transfer object retrieved from the repository
     */
    @Override
    public GroupTransferData findByUID(String UID) {
        return null;
    }

    /**
     * Return all the Group data transfer objects in the repository.
     * @return all the Group data transfer objects in the repository
     */
    @Override
    public List<GroupTransferData> findAll() {
        return null;
    }

    /**
     * Save a new Group data transfer object in the repository.
     * @param group the Group data transfer object to save
     * @return the UID of the new Group data transfer object
     */
    @Override
    public String save(GroupTransferData group) {
        return null;
    }

    /**
     * Update an existing Group data transfer object in the repository to match the given object.
     * @param groupTransferData the Group data transfer object with which to update the repository
     * @return whether the Group data transfer object was updated in the repository
     */
    @Override
    public boolean update(GroupTransferData groupTransferData) {
        return false;
    }

    /**
     * Delete the Group data transfer object with the given UID.
     * @param UID the UID of the Group data transfer object to be deleted
     * @return whether the Group data transfer object was deleted
     */
    @Override
    public boolean deleteById(String UID) {
        return false;
    }
}
