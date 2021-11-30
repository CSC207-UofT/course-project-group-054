package com.example.compound.services;

import com.example.compound.entities.User;
import com.example.compound.exceptions.UserAuthException;

// Use Case Interactor
public interface UserService {
    User authenticateUser(String email, String password)
            throws UserAuthException;
    User registerUser(String firstName, String lastName,
                      String email, String password)
            throws UserAuthException;
}
