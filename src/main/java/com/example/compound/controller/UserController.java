package com.example.compound.controller;

import com.example.compound.data.Data;
import com.example.compound.entities.User;
import com.example.compound.use_cases.ExpenseManager;
import com.example.compound.use_cases.GroupManager;
import com.example.compound.use_cases.gateways.RepositoryGateway;
import com.example.compound.use_cases.UserManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter;
    public RepositoryGateway repositoryGateway;
    public GroupManager groupManager;
    public UserManager userManager;
    public ExpenseManager expenseManager;

    public UserController(
//            RepositoryGateway repositoryGateway // TODO: Error "Could not autowire"
    ) {
        counter = new AtomicLong();
        this.repositoryGateway = new Data(); // TODO: Take in as a parameter?
//        this.repositoryGateway = repositoryGateway;
        this.groupManager = new GroupManager(this.repositoryGateway);
        this.userManager = new UserManager(this.repositoryGateway);
        this.expenseManager = new ExpenseManager(this.repositoryGateway);
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

        User user = userManager.getUser(email);
        if (user != null) {
            return user.toString();
        } else {
            return "No user found.";
        }
    }
}
