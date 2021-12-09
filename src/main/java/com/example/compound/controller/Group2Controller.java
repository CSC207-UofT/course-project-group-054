package com.example.compound.controller;

import com.example.compound.api.entities.Group;
import com.example.compound.api.repositories.Group2Repo;
import com.example.compound.use_cases.Group2Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/groups")
public class Group2Controller {
    @Qualifier("group2Repo")
    @Autowired
    Group2Repo repository;

    Group2Manager manager = new Group2Manager();


    /**
     * API Endpoint to create a group
     * @param request JSON request object casted into a Java map with as <String, Object> key value pairs
     * @return 1 if the group was created
     */
    @PostMapping("/add")
    public int createGroup(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String description = (String) request.get("description");

        ArrayList<Integer> json = (ArrayList<Integer>) request.get("members");
        System.out.println(json);
        Group group = new Group(name, description, json, new ArrayList<>(), new ArrayList<>());
        repository.save(group);
        return 1;
    }

    /**
     * API Endpoint to delete a group, if it exists, from the database
     * @param guid The uid of the group to be deleted from the database.
     * @return 1 if the group was successfully deleted or does not exist, 0 otherwise
     */
    @PostMapping("/{guid}/delete")
    public int deleteGroup(@PathVariable Integer guid) {
        repository.deleteById(guid);
        return 1;
    }

    @GetMapping("/all")
    public List<Group> showAllGroups() {
        return repository.findAll();
    }

    @GetMapping("/{guid}")
    public Optional<Group> getById(@PathVariable Integer guid) {
        return repository.findById(guid);
    }


    @PostMapping("/{guid}/{attribute}/add")
    public List<Integer> addObject(@PathVariable Integer guid,
                              @PathVariable String attribute,
                              @RequestBody Map<String, Object> request) {
        Group group = repository.getById(guid);

        if (attribute.equals("expenses")) {
            Integer euid = (Integer) request.get("expenseId");
            // TODO check if expense object exists in expenses table

            manager.addExpense(group, euid);
            repository.save(group);
            return group.getExpenses();
        } else if (attribute.equals("members")) {
            Integer uuid = (Integer) request.get("userId");
            manager.addMember(group, uuid);
            repository.save(group);
            return group.getMembers();
        } else {
            return null;
        }
    }

    @PostMapping("/{guid}/{attribute}/remove")
    public List<Integer> deleteObject(@PathVariable Integer guid,
                                    @PathVariable String attribute,
                                    @RequestBody Map<String, Object> request) {
        Group group = repository.getById(guid);
        // What if the group with given id does not exist?

        System.out.println("Debug");
        System.out.println(group);

        if (attribute.equals("expenses")) {
            Integer euid = (Integer) request.get("expenseId");
            manager.removeExpense(group, euid);
            repository.save(group);
            return group.getExpenses();
        } else if (attribute.equals("members")) {
            Integer uuid = (Integer) request.get("userId");
            manager.removeMember(group, uuid);
            repository.save(group);
            return group.getMembers();
        }
        else {
            return null;
        }
    }

}
