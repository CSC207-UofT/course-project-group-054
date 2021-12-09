package com.example.compound.api.repositories;

import com.example.compound.entities.User;
import com.example.compound.exceptions.UserAuthException;

import java.util.List;
import java.util.Map;

public interface UserInteractor {
    Integer create(String firstName, String lastName, String email, String password) throws UserAuthException;
    User findByEmailAndPassword(String email, String password) throws UserAuthException;
    Integer getCountByEmail(String email);
    User findById(int id);
    List<Map<String, Object>> listAllUsers();
}






//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//public interface UserRepository extends JpaRepository<User, Long> {
////    Integer create(String firstName, String lastName, String email, String password) throws UserAuthException;
////    User findByEmailAndPassword(String email, String password) throws UserAuthException;
////    Integer getCountByEmail(String email);
////    User findById(Integer userId);
//}