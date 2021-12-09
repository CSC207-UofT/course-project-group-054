package com.example.compound.api.controller;

import com.example.compound.api.entities.Group;
import com.example.compound.api.repositories.GroupInteractor;
import com.example.compound.api.use_cases.GroupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Qualifier("groupInteractor")
    @Autowired
    GroupInteractor repository;

    final GroupManager manager = new GroupManager();


    /**
     * API Endpoint to create a group
     * @param request JSON object containing information of the new group
     * @return 1 if the group was created
     */
    @PostMapping("/create")
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

    /**
     * API Endpoint to return a list of all groups in the database.
     * @return list of all groups in the database
     */
    @GetMapping("/all")
    public List<Group> showAllGroups() {
        return repository.findAll();
    }


    /**
     * API Endpoint to get a group from guid
     * @param guid UID of the concerned group
     * @return JSON object containing details of the group if it exists, null otherwise
     */
    @GetMapping("/{guid}")
    public Optional<Group> getById(@PathVariable Integer guid) {
        return repository.findById(guid);
    }


    /**
     * API Endpoint to add to a group attribute
     * @param guid UID of the concerned group
     * @param attribute Attribute to be modified
     * @param request JSON object containing information about the modification
     * @return Updated attribute
     */
    @PostMapping("/{guid}/{attribute}/add")
    public List<Integer> addObject(@PathVariable Integer guid,
                              @PathVariable String attribute,
                              @RequestBody Map<String, Object> request) {
        Group group = repository.getById(guid);

        if (attribute.equals("expenses")) {
            Integer euid = (Integer) request.get("expenseId");
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

    /**
     * API Endpoint to remove from a group attribute
     * @param guid UID of the concerned group
     * @param attribute Attribute to be modified
     * @param request JSON object containing information about the modification
     * @return Updated attribute
     */
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
