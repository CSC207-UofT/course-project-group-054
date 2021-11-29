package com.example.compound.controller;

//import com.example.compound.api.UserInteractor;
import com.example.compound.entities.User;
import com.example.compound.exceptions.UserAuthException;
import com.example.compound.repositories.UserRepository;
import com.example.compound.repositories.UserRepositoryImpl;
import com.example.compound.use_cases.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository repository;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/users")
    List<String> all() {
        List<String> users = new ArrayList<>();
        users.add("new user");
        return users;
//        return repository.listAllUsers();
    }

    public UserController() {
        this.repository = new UserRepositoryImpl();
    }

    @GetMapping("/users/id/{id}")
    public User getUserByUuid(@PathVariable int id) {

//        try {
            return repository.findById(id);
//        } catch (Exception ignored) { }

//        return new User(id, "Test", "", "", 0.00, "s");
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name;
    }

    @GetMapping("/user-groups")
    public String groups(@RequestParam(value = "email", defaultValue = "") String email) {
        if (email.equals("")) {
            return "ERROR: Provide a valid email";
        }

        User user = UserManager.getUser(email);
        if (user != null) {
            return user.toString();
        } else {
            return "No user found.";
        }
    }


    /**
     * API POST path to create a new user in database.
     * @param request The body of the request
     * @return 1 iff user was successfully created, 0 otherwise.
     */
    @PostMapping("/create-new-user")
    public int createUser(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String email = (String) request.get("email");
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        try {
            repository.create(name, email, username, password);

        } catch (Exception ignored) { }

        return 0;
    }
}
