package com.example.compound.use_cases.gateways;

import java.util.List;

/**
 * A repository for storing objects of a specific type.
 * @param <T> the type of object to be stored in the repository
 */
public interface RepositoryGatewayI<T> {
    /**
     * Return the object with the given UID from the repository.
     * @param UID the UID of the object to be retrieved
     * @return the object retrieved from the repository
     */
    T findByUID(String UID);

    /**
     * Return all the objects in the repository.
     * @return all the objects in the repository
     */
    List<T> findAll();

    /**
     * Save a new object in the repository.
     * @param t the object to save
     * @return the UID of the new object
     */
    String save(T t);

    /**
     * Update an existing object in the repository to match the given object.
     * @param t the object with which to update the repository
     * @return whether the object was updated in the repository
     */
    boolean update(T t);

    /**
     * Delete the object with the given UID.
     * @param UID the UID of the object to be deleted
     * @return whether the object was deleted
     */
    boolean deleteById(String UID);
}
