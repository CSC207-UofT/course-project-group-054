package com.example.compound.use_cases.gateways;

import java.util.List;

public interface RepositoryGatewayI<T> { // TODO: Delete old RepositoryGateway and rename this to RepositoryGateway
    T findByUID(String UID);

    List<T> findAll();

    // Returns a new UID
    String save(T t); // TODO: Separate methods for saving a new object and updating an existing one?

    boolean update(T t);

    boolean deleteById(String UID);
}
