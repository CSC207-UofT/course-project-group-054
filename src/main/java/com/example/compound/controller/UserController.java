package com.example.compound.controller;

import com.example.compound.entities.User;
import com.example.compound.use_cases.UserManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

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
}
