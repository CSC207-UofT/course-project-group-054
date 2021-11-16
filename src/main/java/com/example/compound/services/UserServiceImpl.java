package com.example.compound.services;

import com.example.compound.entities.User;
import com.example.compound.exceptions.UserAuthException;
import com.example.compound.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

//    @Autowired
//    UserRepository userRepository;

    @Override
    public User authenticateUser(String email, String password) throws UserAuthException {
//        if(email != null) email = email.toLowerCase();
//        return userRepository.findByEmailAndPassword(email, password);
        return null;
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws UserAuthException {
//        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
//        if(email != null) email = email.toLowerCase();
//        if(!pattern.matcher(email).matches())
//            throw new UserAuthException("Invalid email format");
//        Integer count = userRepository.getCountByEmail(email);
//        if(count > 0)
//            throw new UserAuthException("Email already in use");
//        Integer userId = userRepository.create(firstName, lastName, email, password);
//        return userRepository.findById(userId);



        return null;
    }
}
